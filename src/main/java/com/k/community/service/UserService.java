package com.k.community.service;

import com.k.community.dao.UserMapper;
import com.k.community.entity.User;
import com.k.community.util.CommunityConstant;
import com.k.community.util.CommunityUtil;
import com.k.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName: UserService
 * @Description: UserService
 * @Author 77166
 * @Date 2021/3/26
 */
@Service
public class UserService implements CommunityConstant {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MailClient mailClient;
    @Autowired
    private  TemplateEngine templateEngine;

    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${community.path.domain}")
    private String domain;

    public User findUserById(int id){
        return userMapper.selectById(id);
    }

    public Map<String,Object> register(User user){
        HashMap<String, Object> map = new HashMap<>();
        if(user==null){
            throw new IllegalArgumentException("参数不能为空");
        }
        if(StringUtils.isBlank(user.getUsername())){
            map.put("usernameMsg","账号不能为空");
            return map;
        }
        if(StringUtils.isBlank(user.getPassword())){
            map.put("pwdMsg","密码不能为空");
            return map;
        }
        if(StringUtils.isBlank(user.getEmail())){
            map.put("emailMsg","邮箱不能为空");
            return map;
        }

        User u = userMapper.selectByName(user.getUsername());
        if(u!=null) {
            map.put("usernameMsg","账号已存在");
            return map;
        }
        u = userMapper.selectByEmail(user.getEmail());
        if(u!=null) {
            map.put("emailMsg","邮箱已被注册");
            return map;
        }

        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5(user.getPassword()+user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        //激活邮件
        Context context=new Context();
        context.setVariable("email",user.getEmail());
        String url=domain+contextPath+"/activation/"+user.getId()+"/"+user.getActivationCode();
        context.setVariable("url",url);
        String content=templateEngine.process("/mail/activation",context);
        mailClient.sendMail(user.getEmail(),"激活账号",content);
        return map;
    }

    public int activation(int id,String code){
        User user = userMapper.selectById(id);
        if(user.getStatus()==1){
            return ACTIVATION_REPEAT;
        }
        if(user.getActivationCode().equals(code)){
            userMapper.updateStatus(id,1);
            return ACTIVATION_SUCCESS;
        }
        return ACTIVATION_ERROR;

    }
}

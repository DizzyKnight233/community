package com.k.community.controller;

import com.google.code.kaptcha.Producer;
import com.k.community.entity.User;
import com.k.community.service.UserService;
import com.k.community.util.CommunityConstant;
import com.k.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * @ClassName: LoginController
 * @Description: LoginController
 * @Author 77166
 * @Date 2021/3/29
 */
@Controller
public class LoginController implements CommunityConstant {
    @Autowired
    private UserService userService;

    @Autowired
    private Producer producer;

    @Value("server.servlet.context-path")
    private String contextPath;

    private static final Logger logger= LoggerFactory.getLogger(MailClient.class);

    @RequestMapping(value = "/loginPage",method = RequestMethod.GET)
    public String getLoginPage(){
        return "site/login";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String getRegisterPage(){
        return "site/register";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(Model model, User user){
        Map<String, Object> map = userService.register(user);
        if(map==null||map.isEmpty()){
            model.addAttribute("msg","注册成功，我们已经向您的邮箱发送了一封激活邮件，请尽快激活");
            model.addAttribute("target","/index");
            return "/site/operate-result";
        }else{
            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("emailMsg",map.get("emailMsg"));
            model.addAttribute("pwdMsg",map.get("pwdMsg"));
            return "site/register";
        }
    }

    @RequestMapping(value = "/activation/{id}/{active}",method = RequestMethod.GET)
    public String activation(@PathVariable("id") int id, @PathVariable("active") String code, Model model){
        int result = userService.activation(id, code);
        if(result==ACTIVATION_SUCCESS){
            model.addAttribute("msg","您的账号已经激活成功,可以正常使用了!");
            model.addAttribute("target","/login");
        }else if(result==ACTIVATION_REPEAT){
            model.addAttribute("msg","您的账号已经激活过了,请直接登录!");
            model.addAttribute("target","/login");
        }else {
            model.addAttribute("msg","激活失败，请检查连接是否正确!");
            model.addAttribute("target","/index");
        }
        return  "/site/operate-result";
    }

    @RequestMapping(value = "/kaptcha",method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response, HttpSession session){
        String text = producer.createText();
        BufferedImage image = producer.createImage(text);
        session.setAttribute("kaptchaText",text);
        response.setContentType("image/png");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image,"png",outputStream);
        } catch (IOException e) {
            logger.error("响应验证码失败："+e.getMessage());
        }
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String username,String password,String code,boolean rememberMe,Model model,
                        HttpSession session,HttpServletResponse response){
        String kaptchaText = (String) session.getAttribute("kaptchaText");
        if(StringUtils.isBlank(kaptchaText)||StringUtils.isBlank(code)|| !kaptchaText.equalsIgnoreCase(code)){
            model.addAttribute("codeMsg","验证码错误");
            return "/site/login";
        }
        int expirySeconds=rememberMe?REMEMBER_EXPIRED_SECONDS:DEFAULT_EXPIRED_SECONDS;
        Map<String, Object> map = userService.login(username, password, expirySeconds);
        if(map.containsKey("loginTicket")){
            Cookie cookie=new Cookie("ticket",map.get("loginTicket").toString());
            cookie.setPath(contextPath);
            cookie.setMaxAge(expirySeconds);
            response.addCookie(cookie);
            return "redirect:/index";
        }else{
            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("pwdMsg",map.get("pwdMsg"));
            return "site/login";
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/loginPage";
    }




}

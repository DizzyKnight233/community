package com.k.community.service;

import com.k.community.dao.UserMapper;
import com.k.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: UserService
 * @Description: UserService
 * @Author 77166
 * @Date 2021/3/26
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserById(int id){
        return userMapper.selectById(id);
    }
}

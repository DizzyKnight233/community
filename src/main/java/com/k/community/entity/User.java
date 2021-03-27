package com.k.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
*@ClassName: User
*@Description: User
*@Author 77166
*@Date 2021/3/26
*
*/
@Data
@NoArgsConstructor
public class User {

    public User(String username, String password, String salt, String email, Integer type, Integer status, String activationCode, String headerUrl, Date createTime) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.email = email;
        this.type = type;
        this.status = status;
        this.activationCode = activationCode;
        this.headerUrl = headerUrl;
        this.createTime = createTime;
    }


    private Integer id;

    private String username;

    private String password;

    private String salt;

    private String email;

    /**
    * 0-普通用户; 1-超级管理员; 2-版主;
    */
    private Integer type;

    /**
    * 0-未激活; 1-已激活;
    */
    private Integer status;

    private String activationCode;

    private String headerUrl;

    private Date createTime;
}
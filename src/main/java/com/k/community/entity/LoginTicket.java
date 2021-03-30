package com.k.community.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*@ClassName: LoginTicket
*@Description: LoginTicket
*@Author 77166
*@Date 2021/3/30
*
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginTicket {
    private Integer id;

    private Integer userId;

    private String ticket;

    /**
    * 0-有效; 1-无效;
    */
    private Integer status;

    private Date expired;
}
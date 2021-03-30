package com.k.community.dao;

import com.k.community.entity.LoginTicket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
*@ClassName: LoginTicketMapper
*@Description: LoginTicketMapper
*@Author 77166
*@Date 2021/3/30
*
*/
@Mapper
public interface LoginTicketMapper {
    int insertLoginTicket(LoginTicket loginTicket);

    LoginTicket selectByTicket(String ticket);

    int updateStatus(@Param("ticket") String ticket, @Param("status") int status);
}
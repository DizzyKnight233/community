package com.k.community.dao;

import com.k.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
*@ClassName: UserMapper
*@Description: UserMapper
*@Author 77166
*@Date 2021/3/26
*
*/
@Mapper
public interface UserMapper {
    User selectById(Integer id);

    User selectByName(String name);

    User selectByEmail(String email);

    int insertUser(User user);

    int updateStatus(@Param("id") int id,@Param("status") int status);

    int updateHeader(@Param("id") int id,@Param("head")String head);

    int updatePassword(@Param("id") int id,@Param("password")String password);
}
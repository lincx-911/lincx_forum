package com.lincx.loginproject.dao;

import com.lincx.loginproject.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("select * from users where username=#{username}")
    public User getUserByname(String username) throws Exception;
    @Select("select * from users where email=#{email}")
    public User getUserByemail(String email) throws Exception;
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    @Insert("insert into users(username,password,email,regdate) values(#{username},#{password},#{email},#{regdate})")
    public int insertUser(User user) throws Exception;
    @Update("update users set password=#{password} where email=#{email}")
    public int updateUserPsw(@Param("email") String email,@Param("password") String password);
}

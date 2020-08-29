package com.lincx.loginproject.service;

import com.lincx.loginproject.model.User;

public interface UserService {
    //判断用户是否存在
    public boolean UsernameExit(String username);
    //判断邮箱是否存在
    public boolean EmaiExit(String email);
    //添加user
    public int AddUser(User user);
    //选择user
    public User getUserbyName(String username);
    public User getUserbyEmail(String email);
    public boolean updateUserPsw(String email,String password);
}

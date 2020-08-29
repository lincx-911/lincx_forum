package com.lincx.loginproject.service.impl;

import com.lincx.loginproject.dao.UserMapper;
import com.lincx.loginproject.model.User;
import com.lincx.loginproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper usermapper;

    @Override
    public boolean UsernameExit(String username) {

        if(getUserbyName(username)==null){
           
            return false;
        }
       return true;
    }

    @Override
    public boolean EmaiExit(String email) {

        if(getUserbyName(email)==null){

            return false;
        }
        return true;
    }

    @Override
    public int AddUser(User user) {
        int id;
        try{
            id = usermapper.insertUser(user);
        }
        catch (Exception exception){

            return -1;
        }
        return id;
    }

    @Override
    public User getUserbyName(String username) {

        User user = new User();
        try {
            user = usermapper.getUserByname(username);
        }
        catch (Exception exception){

            return null;
        }

        return user;
    }

    @Override
    public User getUserbyEmail(String email) {

        User user = new User();
        try {
            user = usermapper.getUserByemail(email);
        }
        catch (Exception exception){

            return null;
        }

        return user;
    }
    @Override
    public boolean updateUserPsw(String email,String password){
        try {
        if(usermapper.updateUserPsw(email,password)>0){
            return true;
        }
        return false;
        }
        catch (Exception exception){
            return false;
        }
    }
}

package com.lincx.loginproject.controller;

import com.lincx.loginproject.model.User;
import com.lincx.loginproject.service.impl.UserServiceImpl;
import com.lincx.loginproject.utils.JsonWebTokenUtil;
import com.lincx.loginproject.utils.MD5SHAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class Login {
    @Autowired
    private UserServiceImpl userService;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> login(@RequestParam(value = "account",required = false) String account,
                                                    @RequestParam(value = "password",required = false) String password) throws IOException, NoSuchAlgorithmException {


        Map<String,Object> result = new HashMap<String, Object>();

        if(account==null||password==null){
            result.put("msg","参数传输格式错误");
            result.put("code",1);

            return new ResponseEntity<Map<String, Object>>(result,HttpStatus.OK);
        }
        User user = new User();
        result.put("token","");
        user=userService.getUserbyName(account);

        if(user==null){
            user = userService.getUserbyEmail(account);
            if(user==null){
                result.put("msg","账号不存在");
                result.put("code",5);
                return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
            }
        }
        if(!user.getPassword().equals(MD5SHAUtil.MD5SHA(password))){
            result.put("msg","账号或密码错误");
            result.put("code",7);
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
        }

        String token = JsonWebTokenUtil.CreateJWT(user.getId(),user.getUsername(),user.getRole());
        result.put("token",token);

        user.setPassword("");
        result.put("user",user);
        result.put("msg","登录成功");
        result.put("code",0);

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/regist",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> registe(@RequestParam(value = "username",required = false) String username,
                                                      @RequestParam(value = "password",required = false) String password,
                                                      @RequestParam(value = "email",required = false) String email) throws NoSuchAlgorithmException {

        Map<String,Object> result = new HashMap<String, Object>();
        if(username==null||password==null||email==null){
            result.put("msg","参数传输格式错误");
            result.put("code",1);
            return new ResponseEntity<Map<String, Object>>(result,HttpStatus.OK);
        }
        if(userService.UsernameExit(username)){
            System.out.println("用户名已被注册");
            result.put("msg","用户名已被注册");
            result.put("code",8);
            return new ResponseEntity<Map<String, Object>>(result,HttpStatus.OK);
        }
        if(userService.EmaiExit(email)) {
            System.out.println("邮箱已被注册");
            result.put("msg", "邮箱已被注册");
            result.put("code",9);
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5SHAUtil.MD5SHA(password));
        user.setEmail(email);
        user.setRegdate(new Date());
        int id = userService.AddUser(user);
        if (id==-1){
            System.out.println("服务器错误");
            result.put("msg", "服务器错误");
            result.put("code",2);
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
        }
        result.put("msg","注册成功");
        result.put("code",0);

        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/changepsw",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> changePassword(@RequestParam(value = "email",required = false) String email,
                                                             @RequestParam(value = "password",required = false) String password)throws NoSuchAlgorithmException{
        Map<String,Object> result = new HashMap<String, Object>();
        password = MD5SHAUtil.MD5SHA(password);

        if(!userService.updateUserPsw(email,password)){
            System.out.println("服务器错误");
            result.put("msg", "服务器错误");
            result.put("code",2);
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
        }
        result.put("msg","修改成功");
        result.put("code",0);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
}

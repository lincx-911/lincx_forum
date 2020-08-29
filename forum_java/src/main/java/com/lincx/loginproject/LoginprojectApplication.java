package com.lincx.loginproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.lincx.loginproject.dao")
@SpringBootApplication
public class LoginprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginprojectApplication.class, args);
    }

}

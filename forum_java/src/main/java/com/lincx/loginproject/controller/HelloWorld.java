package com.lincx.loginproject.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorld {
    @RequestMapping("/say")
    public String say(){
        return "Hello World";
    }
}

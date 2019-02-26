package com.lyn.practice.controller;


import com.lyn.practice.autoconfig.SayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SayBySqlController {

    @Autowired
    private SayService sayService;

    @GetMapping(value = "/sayBySql")
    public String say(String message){
        return  sayService.sayHello(message);
    }

}

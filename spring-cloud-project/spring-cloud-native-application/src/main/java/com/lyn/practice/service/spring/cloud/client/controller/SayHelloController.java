package com.lyn.practice.service.spring.cloud.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuyanan on 2018-09-25.
 */
@RestController
public class SayHelloController {

    @RequestMapping(value = "sayHello/{message}")
    public String sayHello(@PathVariable(value = "message")String message){
        return "hello,"+message;
    }
}

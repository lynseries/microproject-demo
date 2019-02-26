package com.lyn.practice.service.spring.cloud.server.controller;

import com.lyn.practice.service.spring.cloud.server.annotation.LimitVisit;
import com.lyn.practice.service.spring.cloud.server.annotation.TimeoutServiceBreaker;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class SayController {




    @HystrixCommand(
            fallbackMethod ="handleErr",
        commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "100")}
    )
    @GetMapping(value = "say")
    public String say(@RequestParam(value = "message") String message){
        Random random = new Random();
        long mockTime = random.nextInt(200);
        try {
            TimeUnit.MILLISECONDS.sleep(mockTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String msg = "Server say == >  hello,lmm,"+message;
        System.out.println(msg);
        return msg;
    }

    @GetMapping(value = "say1")
    @TimeoutServiceBreaker(timeout = 100)
    @LimitVisit(3)
    public String say1(@RequestParam(value = "message") String message){
        Random random = new Random();
        long mockTime = random.nextInt(200);
        try {
            TimeUnit.MILLISECONDS.sleep(mockTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String msg = "Server say1  == >  hello,lmm,"+message;
        System.out.println(msg);
        return msg;
    }


    public String handleErr(String message){
        return "Fail:"+message;
    }


}

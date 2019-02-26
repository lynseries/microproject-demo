package com.lyn.practice.service.spring.cloud.client.controller;

import com.lyn.practice.service.spring.cloud.client.service.RestSayingService;
import com.lyn.practice.service.spring.cloud.client.service.SayingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenFeignController {

    @Autowired
    private SayingService sayingService;

    @Autowired
    private RestSayingService restSayingService;

    @GetMapping(value = "/openFiegn/say")
    public String openFiegnSay(@RequestParam("message") String message){
        return sayingService.feignSay(message);
    }

    @GetMapping(value = "/restClient/say")
    public String restSay(@RequestParam("message") String message){
        return restSayingService.restSay(message);
    }
}

package com.lyn.practice.service.spring.cloud.client.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "spring-cloud-server-application")
@Component
public interface SayingService {

    @RequestMapping(value = "/say1")
    public String feignSay(@RequestParam("message") String message);

}

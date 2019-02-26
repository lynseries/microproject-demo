package com.lyn.practice.service.spring.cloud.client.service;

import com.lyn.practice.service.spring.cloud.client.annotation.RestClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestClient("spring-cloud-server-application")
@Component
public interface RestSayingService {

    @GetMapping("/say1")
    public String restSay(@RequestParam("message") String message);

}

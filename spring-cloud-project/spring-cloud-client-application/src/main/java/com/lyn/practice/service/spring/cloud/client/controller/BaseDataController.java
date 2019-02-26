package com.lyn.practice.service.spring.cloud.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BaseDataController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/ids")
    public List<String> getServiceIds(){
        return discoveryClient.getServices();
    }
}

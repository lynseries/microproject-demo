package com.lyn.practice.service.spring.cloud.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RonbinLbController {

    @Autowired
    @LoadBalanced
    private RestTemplate ronbinRestTemplate;


    @GetMapping(value = "/invoke/ronbin/{serviceId}/say")
    public String invokeSay(@PathVariable String serviceId,@RequestParam("message")String message){
        String targetUrl = "http://"+serviceId+"/say?message="+message;
        String result = ronbinRestTemplate.getForObject(targetUrl,String.class);
        return result;
    }

    @Bean
    @LoadBalanced
    public RestTemplate ronbinRestTemplate(){
        return new RestTemplate();
    }
}

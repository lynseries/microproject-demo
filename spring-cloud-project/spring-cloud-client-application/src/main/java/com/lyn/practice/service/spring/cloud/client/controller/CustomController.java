package com.lyn.practice.service.spring.cloud.client.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
public class CustomController {

    private static Logger logger = LoggerFactory.getLogger(CustomController.class);

    @Autowired
    @Qualifier(value = "restTemplate")
    private RestTemplate restTemplate;

    private Map<String,Set<String>> targetUrlsCache = new ConcurrentHashMap<>();

    @Autowired
    private DiscoveryClient discoveryClient;

    @Scheduled(fixedRate = 10 * 1000)
    public void updateTargetUrlsCache(){
        Map<String,Set<String>> newTargetUrlsMap = new HashMap<>();
        List<String> serviceNames = discoveryClient.getServices();
        for (String serviceId:serviceNames) {
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceId);
            Set<String> targetUrlSet = serviceInstances
                    .stream()
                    .map(s-> s.isSecure()
                            ? "https://"+s.getHost()+":"+s.getPort()
                            : "http://"+s.getHost()+":"+s.getPort()
                    ).collect(Collectors.toSet());
            newTargetUrlsMap.put(serviceId,targetUrlSet);
        }
        this.targetUrlsCache = newTargetUrlsMap;
    }




    @GetMapping("/invoke/{serviceName}/say")
    public String say(@PathVariable String serviceName,@RequestParam String message){
        String serviceId = serviceName;
        Map<String,Set<String>> map = this.targetUrlsCache;
        Set<String> targetUrlsSet = map.get(serviceId);
        List<String> targetUrlList = new LinkedList<>(targetUrlsSet);
        int index = new Random().nextInt(targetUrlList.size());
        String targetUrl = targetUrlList.get(index);
        String finalUrl=targetUrl+"/say?message="+message;
        logger.info("targetUrl = "+finalUrl);
        String result = restTemplate.getForObject(finalUrl,String.class);
        return result;
    }

    @Bean
    @Qualifier(value = "restTemplate")
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}

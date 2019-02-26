package com.lyn.practice.service.spring.cloud.client.discovery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/zk")
public class ZkServiceController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "service/{serviceId}")
    public List<ServiceInstance> getServiceInfo(@PathVariable(value = "serviceId") String serviceId) {

        List<ServiceInstance> serviceInstanceList = new ArrayList<>();

        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceId);
        serviceInstanceList.addAll(serviceInstances);

        return serviceInstanceList;
    }

    @GetMapping(value = "service/ids")
    public List<String> getAllServiceIds(){
        return this.discoveryClient.getServices();
    }

}

package com.lyn.practice.service.spring.cloud.client.discovery.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.lyn.practice.discovery.controller")
public class ZkDiscoveryClient {

    public static void main(String[] args) {
        SpringApplication.run(ZkDiscoveryClient.class,args);
    }
}

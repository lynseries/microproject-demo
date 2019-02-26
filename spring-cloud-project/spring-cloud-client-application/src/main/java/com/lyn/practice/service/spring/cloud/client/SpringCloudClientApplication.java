package com.lyn.practice.service.spring.cloud.client;

import com.lyn.practice.service.spring.cloud.client.annotation.EnableRestClient;
import com.lyn.practice.service.spring.cloud.client.service.RestSayingService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableFeignClients
@EnableRestClient(clients = RestSayingService.class)
public class SpringCloudClientApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder(SpringCloudClientApplication.class)

                .run(args);
    }
}

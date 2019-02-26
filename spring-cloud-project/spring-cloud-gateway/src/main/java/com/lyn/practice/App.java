package com.lyn.practice;

import com.lyn.practice.gateway.loadbanlance.ZookeeperLoadBalancer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@ServletComponentScan(basePackages = "com.lyn.practice.gateway")
@EnableScheduling
public class App {
    public static void main( String[] args ) {

        SpringApplication.run(App.class,args);

    }

    @Bean
    public ZookeeperLoadBalancer zookeeperLoadBalancer(DiscoveryClient discoveryClient){
        return new ZookeeperLoadBalancer(discoveryClient);
    }
}

package com.lyn.practice.service.spring.cloud.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApp {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApp.class);
    }


    @Bean
    public EnvironmentRepository environmentRepository(){
        EnvironmentRepository repository = new EnvironmentRepository() {
            @Override
            public Environment findOne(String application, String profile, String label) {
                Environment environment = new Environment("myconfig1","default");
                List<PropertySource> propertySourceList = environment.getPropertySources();
                Map<String,Object> map  = new HashMap<>();
                map.put("name","liumuchen");
                PropertySource propertySource = new PropertySource("map",map);
                propertySourceList.add(propertySource);
                return environment;
            }
        };

        return repository;
    }


    
}

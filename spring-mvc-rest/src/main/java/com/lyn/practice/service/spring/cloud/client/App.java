package com.lyn.practice.service.spring.cloud.client;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.lyn.practice.controller")
public class App 
{
    public static void main( String[] args )
    {
        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(App.class);
        springApplicationBuilder.run(args);
    }
}

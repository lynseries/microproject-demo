package com.lyn.practice.service.spring.cloud.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@EnableAutoConfiguration
@RestController
public class App 
{
    public static void main( String[] args )
    {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.setId("lyn");
        context.registerBean("message", String.class, "hello world");
        context.registerBean("service",String.class,"hello world333");
        context.refresh();

        //System.out.println( "Hello World!" );
        //SpringApplication.run(App.class,args);
        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(App.class);
        springApplicationBuilder.parent(context);
        springApplicationBuilder.run(args);
    }

    @Autowired
    @Qualifier(value = "service")
    private String message;

    @RequestMapping("")
    public String sayHello(){

        return "hello bean," + message;
    };


    @Bean
    public String sayHelloBean(){
        message = "hello 999999";
        return message;
    }


}

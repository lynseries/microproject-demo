package com.lyn.practice.service.spring.cloud.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@RestController
@EnableConfigServer
public class App 
{
    public static void main( String[] args )
    {
        int[] arrSort = {4,1,3,4,5,3,2,0};
        Arrays.sort(arrSort);
        System.out.println(Arrays.toString(arrSort));
        System.out.println( "Hello World!" );

        SpringApplication.run(App.class, args);


    }

    @Autowired
    private String bean;

    @Value("${spring.cloud.config.server.prefix:}")
    private String path;


    @RequestMapping("/getPath")
    public String getPath(){
        System.out.println("==============path============"+path);
        return path;
    }

    @Value("${user.dir}")
    private String userDir;

    @GetMapping("getUserDir")
    public String getUserDir(){
        return  userDir;
    }

    @GetMapping("getBeanView")
    public String getBeanView(){
        return  bean;
    }

    @Bean
    public String beanName(){
        String beanName =  new String("abc!!!!");
        return beanName;
    }


    @Bean
    public String bean(String beanName){
        String bean =  new String("hello bean"+","+beanName);
        return bean;
    }


}

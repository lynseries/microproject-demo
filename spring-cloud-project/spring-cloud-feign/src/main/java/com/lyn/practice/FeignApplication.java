package com.lyn.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableFeignClients
@EnableSwagger2
public class FeignApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(FeignApplication.class,args);
    }
}

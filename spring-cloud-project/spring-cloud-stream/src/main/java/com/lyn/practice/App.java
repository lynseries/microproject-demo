package com.lyn.practice;

import com.lyn.practice.event.BusChannel;
import com.lyn.practice.stream.LmmChannelService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@EnableBinding(BusChannel.class)
public class App {
    public static void main( String[] args ){

        new SpringApplicationBuilder(App.class).web(WebApplicationType.SERVLET).run(args);
    }
}

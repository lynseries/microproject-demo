package com.lyn.practice;

import com.lyn.practice.event.BusChannel;
import com.lyn.practice.event.bus.StreamRemoteAppEventListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@EnableBinding(BusChannel.class)
public class App 
{
    public static void main( String[] args )
    {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(App.class);

        builder.listeners(new StreamRemoteAppEventListener()).run(args);
    }


    @StreamListener("lmmOut")
    public void onMessage(String message){
        System.out.println("监听到消息:"+ message);
    }
}

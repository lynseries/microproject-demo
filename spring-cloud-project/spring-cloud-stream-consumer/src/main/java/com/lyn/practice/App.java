package com.lyn.practice;

import com.lyn.practice.event.BusChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@EnableBinding(BusChannel.class)
public class App {
    public static void main( String[] args ) {
        SpringApplication.run(App.class,args);
    }

    @StreamListener(BusChannel.inBusChannel)
    public void onMessages(String message){
        System.out.println("@StreamListener listener rabbit mq receive message is:"+message);
    }

    @StreamListener(BusChannel.inBusChannel)
    public void onMessages(byte[] message){
        System.out.println("@StreamListener listener rabbit mq receive message is:"+new String(message));
    }


    @ServiceActivator(inputChannel = BusChannel.inBusChannel)
    public void onMessages1(String message){
        System.out.println("@ServiceActivator listener rabbit mq receive message is:"+message);
    }
}

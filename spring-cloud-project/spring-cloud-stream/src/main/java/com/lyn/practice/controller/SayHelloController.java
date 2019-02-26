package com.lyn.practice.controller;


import com.lyn.practice.event.BusChannel;
import com.lyn.practice.stream.LmmChannelService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SayHelloController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BusChannel busChannel;

    @GetMapping("sayToMq")
    public String sayToMq(@RequestParam(name = "message") String message){
        rabbitTemplate.convertAndSend("hello,world");
        return "ok";
    }

    @GetMapping("sayToMqByChanel")
    public String sayToMqByChanel(@RequestParam(name = "message") String message){
        Message<String> message1 = new GenericMessage<String>(message+",hello world");
        busChannel.getInputChannel().send(message1);
        return "ok";
    }

}

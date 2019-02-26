package com.lyn.practice.controller;


import com.lyn.practice.event.bus.RemoteAppEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SayController implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("/bus/say/{serviceId}")
    public String sayHello(@RequestParam("message") String message,@PathVariable("serviceId") String serviceId){

        RemoteAppEvent remoteAppEvent = new RemoteAppEvent(message,serviceId);
        applicationEventPublisher.publishEvent(remoteAppEvent);

        return "send";
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}

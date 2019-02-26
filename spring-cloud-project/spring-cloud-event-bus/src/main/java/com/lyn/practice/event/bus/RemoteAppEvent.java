package com.lyn.practice.event.bus;

import org.springframework.context.ApplicationEvent;

public class RemoteAppEvent extends ApplicationEvent {

    private String targetService;

    public RemoteAppEvent(Object source,String targetService) {
        super(source);
        this.targetService = targetService;
    }
}

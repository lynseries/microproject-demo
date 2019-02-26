package com.lyn.practice.event.bus;

import com.lyn.practice.event.BusChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

public class StreamRemoteAppEventListener implements SmartApplicationListener {

    private BusChannel busChannel;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if(ContextRefreshedEvent.class.isAssignableFrom(eventType)||RemoteAppEvent.class.isAssignableFrom(eventType)){
            return true;
        }
        return false;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return true;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof ContextRefreshedEvent){
            busChannel = ((ContextRefreshedEvent) event).getApplicationContext().getBean(BusChannel.class);
        }

        if(event instanceof RemoteAppEvent){
            Message<Object> message = new GenericMessage<Object>(event.getSource());
            busChannel.getInputChannel().send(message);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

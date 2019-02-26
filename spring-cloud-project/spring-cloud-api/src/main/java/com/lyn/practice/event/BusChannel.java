package com.lyn.practice.event;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface BusChannel {

    String inBusChannel = "lmmIn";

    String outBusChannel = "lmmOut";

    @Output(outBusChannel)
    MessageChannel getInputChannel();


    @Input(inBusChannel)
    SubscribableChannel getOutputChannel();


}

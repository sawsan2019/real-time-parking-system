package com.parksystem.streamkafka.consumer.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ParkDataStreams {
    String INPUT = "parkdata-in";

    @Input(INPUT)
    SubscribableChannel inboundparkdata();

}

package com.parksystem.streamkafka.processing.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ParkDataStreams {
    String INPUT = "parkdata-in";
    String OUTPUT = "parkdata-out";

    @Input(INPUT)
    SubscribableChannel inboundparkdata();

    @Output(OUTPUT)
    MessageChannel outboundparkdataprocessed();
}
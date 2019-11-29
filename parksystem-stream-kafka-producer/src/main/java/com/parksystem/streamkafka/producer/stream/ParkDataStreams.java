package com.parksystem.streamkafka.producer.stream;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ParkDataStreams {
    String OUTPUT = "parkdata-out";


    @Output(OUTPUT)
    MessageChannel outboundparkdata();
}







package com.parksystem.streamkafka.consumer.service;

import com.parksystem.streamkafka.consumer.model.ParkData;
import com.parksystem.streamkafka.consumer.stream.ParkDataStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class ParkDataListener {
    @StreamListener(ParkDataStreams.INPUT)
    public void handleParkData(@Payload ParkData parkData) {
        log.info("Received Park Data: {}", parkData);
    }
}


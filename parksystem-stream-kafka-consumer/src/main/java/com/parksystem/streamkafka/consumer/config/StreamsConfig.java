package com.parksystem.streamkafka.consumer.config;

import com.parksystem.streamkafka.consumer.stream.ParkDataStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(ParkDataStreams.class)
public class StreamsConfig {
}

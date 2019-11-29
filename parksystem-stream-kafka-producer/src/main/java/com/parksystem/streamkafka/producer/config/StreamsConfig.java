package com.parksystem.streamkafka.producer.config;

import com.parksystem.streamkafka.producer.stream.ParkDataStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(ParkDataStreams.class)
public class StreamsConfig {
}

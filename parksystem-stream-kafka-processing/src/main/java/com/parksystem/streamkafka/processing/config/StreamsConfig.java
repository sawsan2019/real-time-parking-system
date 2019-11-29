package com.parksystem.streamkafka.processing.config;

import com.parksystem.streamkafka.processing.stream.ParkDataStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(ParkDataStreams.class)
public class StreamsConfig {
}

package com.parksystem.streamkafka.consumer.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @Builder
public class ParkData {
    private long timestamp;
    private String message;
}


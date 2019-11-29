package com.parksystem.streamkafka.processing.service;

import com.parksystem.streamkafka.processing.model.ParkData;
import com.parksystem.streamkafka.processing.stream.ParkDataStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
@Slf4j
public class ParkDataService {
	 private final ParkDataStreams parkDatasStreams;

	    public ParkDataService(ParkDataStreams parkDatasStreams) {
	        this.parkDatasStreams = parkDatasStreams;
	    }

	    public void sendParkData(final ParkData parkData) {
	        log.info("Sending park data to new topic parkdataprocessed {}", parkData);

	        MessageChannel messageChannel = parkDatasStreams.outboundparkdataprocessed();
	        messageChannel.send(MessageBuilder
	                .withPayload(parkData)
	                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
	                .build());
	    }
	}
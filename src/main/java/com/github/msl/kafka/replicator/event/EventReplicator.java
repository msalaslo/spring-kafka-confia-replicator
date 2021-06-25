package com.github.msl.kafka.replicator.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.github.msl.kafka.replicator.dto.EventOutDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventReplicator {

	@Autowired
	EventProducer producer;

	@KafkaListener(topics = "${event.consumer.topic.name}", containerFactory = "eventOutKafkaListenerContainerFactory")
	public void eventListener(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Integer key, EventOutDTO eventOut) {
		log.info("Received event message: " + eventOut);
		producer.sendEventWithResult(key, eventOut);
	}

}

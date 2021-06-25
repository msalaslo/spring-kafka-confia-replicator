package com.github.msl.kafka.replicator.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.github.msl.kafka.replicator.dto.EventOutDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventProducer {

	@Autowired
	private KafkaTemplate<Integer, EventOutDTO> eventOutTemplate;
	
	@Autowired
	EventReplicatorConfig config;
	
	public void sendEventWithResult(Integer key, EventOutDTO eventOut) {
		ListenableFuture<SendResult<Integer, EventOutDTO>> future = eventOutTemplate.send(config.getProducerTopicName(), key, eventOut);
		future.addCallback(new ListenableFutureCallback<SendResult<Integer, EventOutDTO>>() {

			@Override
			public void onSuccess(SendResult<Integer, EventOutDTO> result) {
				log.info(
						"Sent message with key=[" + key + ", value=[" + eventOut + "], offset=[" + result.getRecordMetadata().offset() + "]");
			}

			@Override
			public void onFailure(Throwable ex) {
				log.error("Unable to send message=[" + eventOut + "] due to : " + ex.getMessage());
			}
		});
	}
}
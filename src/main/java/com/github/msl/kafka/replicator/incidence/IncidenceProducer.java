package com.github.msl.kafka.replicator.incidence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.github.msl.kafka.replicator.dto.IncidenceDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IncidenceProducer {

	@Autowired
	private KafkaTemplate<Integer, IncidenceDTO> incidenceTemplate;

	@Autowired
	IncidenceReplicatorConfig config;

	public void sendIncidenceWithResult(Integer key, IncidenceDTO incidence) {
		ListenableFuture<SendResult<Integer, IncidenceDTO>> future = incidenceTemplate.send(config.getProducerTopicName(),key, incidence);
		future.addCallback(new ListenableFutureCallback<SendResult<Integer, IncidenceDTO>>() {

			@Override
			public void onSuccess(SendResult<Integer, IncidenceDTO> result) {
				log.info(
						"Sent message with key=[" + key + ", value=[" + incidence + "], offset=[" + result.getRecordMetadata().offset() + "]");
			}

			@Override
			public void onFailure(Throwable ex) {
				log.error("Unable to send message=[" + incidence + "] due to : " + ex.getMessage());
			}
		});
	}
	
	public void sendIncidenceMessage(Integer key, IncidenceDTO incidence) {
		incidenceTemplate.send(config.getProducerTopicName(), key, incidence);
	}
}
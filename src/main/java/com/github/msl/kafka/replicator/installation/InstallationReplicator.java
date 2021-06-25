package com.github.msl.kafka.replicator.installation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.github.msl.kafka.replicator.dto.InstallationOutDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InstallationReplicator {

	@Autowired
	InstallationProducer producer;

	@KafkaListener(topics = "${installation.consumer.topic.name}", containerFactory = "installationOutKafkaListenerContainerFactory")
	public void installationListener(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Integer key, InstallationOutDTO installationOut) {
		log.info("Received installation message: " + installationOut);
		producer.sendEventWithResult(key, installationOut);
	}

}

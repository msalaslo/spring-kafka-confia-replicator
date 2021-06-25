package com.github.msl.kafka.replicator.installation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.github.msl.kafka.replicator.dto.InstallationOutDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InstallationProducer {

	@Autowired
	private KafkaTemplate<Integer, InstallationOutDTO> installationOutTemplate;
	
	@Autowired
	InstallationReplicatorConfig config;
	
	public void sendEventWithResult(Integer key, InstallationOutDTO installationOut) {
		installationOut.setPanelPhoneNumber("555");
		installationOut.setContactName("DUMMY NAME");
		installationOut.setEmail("dummy@mail.com");
		installationOut.setInstallationAddress("DUMMY ADDRESS");
		installationOut.setPanelPhoneNumber("888");
		installationOut.setContactPhoneNumber("999");

		ListenableFuture<SendResult<Integer, InstallationOutDTO>> future = installationOutTemplate.send(config.getConsumerTopicName(), key, installationOut);
		future.addCallback(new ListenableFutureCallback<SendResult<Integer, InstallationOutDTO>>() {

			@Override
			public void onSuccess(SendResult<Integer, InstallationOutDTO> result) {
				log.info(
						"Sent message with key=[" + key + ", value=[" + installationOut + "], offset=[" + result.getRecordMetadata().offset() + "]");
			}

			@Override
			public void onFailure(Throwable ex) {
				log.error("Unable to send message=[" + installationOut + "] due to : " + ex.getMessage());
			}
		});
	}
}
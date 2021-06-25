package com.github.msl.kafka.replicator.incidence;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.msl.kafka.replicator.dto.IncidenceDTO;
import com.github.msl.kafka.replicator.utils.TimestampIncidenceUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IncidenceReplicator {

	@Autowired
	IncidenceProducer producer;
	
	private static Set<Integer> keys = new HashSet<Integer>();

	@KafkaListener(topics = "${incidence.consumer.topic.name}", containerFactory = "incidenceKafkaListenerContainerFactory")
	public void incidenceListener(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Integer key, @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long receivedTimestamp, IncidenceDTO incidence) {
		Long creationTimestamp = incidence.getCreationTimestamp();
		creationTimestamp = TimestampIncidenceUtils.getRealTimestampIncidence(creationTimestamp);
		log.info("Received incidence with receivedTimestamp:" + receivedTimestamp + ", creation timestamp: " + creationTimestamp + ", diff:" + TimestampIncidenceUtils.diffTimestampsSeconds(creationTimestamp, receivedTimestamp)  + ", with key:" + key + ",  message: " + incidence);
		producer.sendIncidenceWithResult(key, incidence);
	}
	
//	@KafkaListener(topics = "${incidence.consumer.topic.name}", containerFactory = "kafkaListenerContainerFactory")
	public void incidenceListenerWithFilter(List<ConsumerRecord<Integer, Object>> records) {
	    ObjectMapper objectMapper = new ObjectMapper();
	    IncidenceDTO incidence = null;
	    for (int i = 0; i < records.size(); i++) {
			ConsumerRecord<Integer, Object> record = (ConsumerRecord<Integer, Object>) records.get(i);
		    try {
				incidence = objectMapper.readValue(record.value().toString(), IncidenceDTO.class);
			} catch (Exception e) {
				log.error("Error parsing record value", e);
			}
		    
			Long creationTimestamp = incidence.getCreationTimestamp();
			creationTimestamp = TimestampIncidenceUtils.getRealTimestampIncidence(creationTimestamp);
			
			Long producedTimestamp = record.timestamp();
			
		    if(incidence.getPriority().equals(25) && isNew(record.key())) {
		    	log.info( i + ", Received incidence with offset:" + record.offset() + ", producedTimestamp: " + producedTimestamp + ", creation timestamp: " + creationTimestamp + ", diff:" + TimestampIncidenceUtils.diffTimestampsSeconds(creationTimestamp, producedTimestamp)  + ", with key:" + record.key() + ",  message: " + incidence);
		    	producer.sendIncidenceMessage(record.key(), incidence);
		    }
		}

	}
	
	private boolean isNew(Integer key) {
		if(!keys.contains(key)) {
			keys.add(key);
			return true;
		} else {
			return false;
		}
	}
	


}

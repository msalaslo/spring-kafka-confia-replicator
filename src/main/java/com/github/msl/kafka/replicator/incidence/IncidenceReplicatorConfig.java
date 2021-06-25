package com.github.msl.kafka.replicator.incidence;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.github.msl.kafka.replicator.dto.IncidenceDTO;

import lombok.Data;

@Configuration
@Data
public class IncidenceReplicatorConfig {

	@Value(value = "${incidence.consumer.bootstrapAddress}")
	private String consumerBootstrapAddress;

	@Value(value = "${incidence.consumer.security.protocol}")
	private String consumerSecurityProtocol;

	@Value(value = "${incidence.consumer.sasl.jaas.config}")
	private String consumerSaslJaasConfig;

	@Value(value = "${incidence.consumer.sasl.mechanism}")
	private String consumerSaslMechanism;

	@Value(value = "${incidence.consumer.topic.name}")
	private String consumerTopicName;

	@Value(value = "${incidence.producer.bootstrapAddress}")
	private String producerBootstrapAddress;

	@Value(value = "${incidence.producer.topic.name}")
	private String producerTopicName;

	@Value(value = "${incidence.producer.security.protocol}")
	private String producerSecurityProtocol;

	@Value(value = "${incidence.producer.sasl.jaas.config}")
	private String producerSaslJaasConfig;

	@Value(value = "${incidence.producer.sasl.mechanism}")
	private String producerSaslMechanism;

	private Map<String, Object> getConsumerProperties() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getConsumerBootstrapAddress());
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "client-incidence-replicator");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put("security.protocol", consumerSecurityProtocol);
		props.put("sasl.mechanism", consumerSaslMechanism);
		props.put("sasl.jaas.config", consumerSaslJaasConfig);
		return props;
	}

	@Bean
	KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, Object>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setConcurrency(3);
		factory.getContainerProperties().setPollTimeout(1000);
		factory.setBatchListener(true);
		return factory;
	}

	@Bean
	public ConsumerFactory<Integer, Object> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(getConsumerProperties());
	}

	public ConsumerFactory<Integer, IncidenceDTO> incidenceConsumerFactory() {
		Map<String, Object> props = getConsumerProperties();
		return new DefaultKafkaConsumerFactory<>(props, new IntegerDeserializer(),
				new JsonDeserializer<>(IncidenceDTO.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<Integer, IncidenceDTO> incidenceKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, IncidenceDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(incidenceConsumerFactory());
		factory.setConcurrency(3);
		factory.getContainerProperties().setPollTimeout(1000);
		factory.setBatchListener(true);
		return factory;
	}

	@Bean
	public ProducerFactory<Integer, IncidenceDTO> incidenceProducerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerBootstrapAddress);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		props.put("security.protocol", producerSecurityProtocol);
		props.put("sasl.mechanism", producerSaslMechanism);
		props.put("sasl.jaas.config", producerSaslJaasConfig);
		return new DefaultKafkaProducerFactory<>(props);
	}

	@Bean
	public KafkaTemplate<Integer, IncidenceDTO> incidenceKafkaTemplate() {
		return new KafkaTemplate<>(incidenceProducerFactory());
	}

}

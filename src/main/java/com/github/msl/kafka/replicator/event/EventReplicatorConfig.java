package com.github.msl.kafka.replicator.event;

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
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.github.msl.kafka.replicator.dto.EventOutDTO;

import lombok.Data;

@Configuration
@Data
public class EventReplicatorConfig {
	
    @Value(value = "${event.consumer.bootstrapAddress}")
    private String consumerBootstrapAddress;
    
	@Value(value = "${event.consumer.security.protocol}")
	private String consumerSecurityProtocol;
	
    @Value(value = "${event.consumer.sasl.jaas.config}")
    private String consumerSaslJaasConfig;
    
    @Value(value = "${event.consumer.sasl.mechanism}")
    private String consumerSaslMechanism;
    
	@Value(value = "${event.consumer.topic.name}")
	private String consumerTopicName;
	
    @Value(value = "${event.producer.bootstrapAddress}")
    private String producerBootstrapAddress;
    
    @Value(value = "${event.producer.topic.name}")
    private String producerTopicName;
    
	@Value(value = "${event.producer.security.protocol}")
	private String producerSecurityProtocol;
	
    @Value(value = "${event.producer.sasl.jaas.config}")
    private String producerSaslJaasConfig;
    
    @Value(value = "${event.producer.sasl.mechanism}")
    private String producerSaslMechanism;
    
    @Bean
    public ProducerFactory<Integer, EventOutDTO> eventOutProducerFactory() {
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
    public KafkaTemplate<Integer, EventOutDTO> eventOutKafkaTemplate() {
        return new KafkaTemplate<>(eventOutProducerFactory());
    }
    
    public ConsumerFactory<Integer, EventOutDTO> eventOutConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerBootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "client-event-replicator");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put("security.protocol", consumerSecurityProtocol);
		props.put("sasl.mechanism", consumerSaslMechanism);
		props.put("sasl.jaas.config", consumerSaslJaasConfig);

		// NO DEJAR EN PRO, SOLO PARA PRUEBAS (Lee desde el primer mensaje)
//		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		
        return new DefaultKafkaConsumerFactory<>(props, new IntegerDeserializer(), new JsonDeserializer<>(EventOutDTO.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Integer, EventOutDTO> eventOutKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, EventOutDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(eventOutConsumerFactory());
        return factory;
    }

}

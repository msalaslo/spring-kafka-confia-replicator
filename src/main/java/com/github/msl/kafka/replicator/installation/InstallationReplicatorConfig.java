package com.github.msl.kafka.replicator.installation;

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

import com.github.msl.kafka.replicator.dto.InstallationOutDTO;

import lombok.Data;

@Configuration
@Data
public class InstallationReplicatorConfig {
	
    @Value(value = "${installation.consumer.bootstrapAddress}")
    private String consumerBootstrapAddress;
    
	@Value(value = "${installation.consumer.security.protocol}")
	private String consumerSecurityProtocol;
	
    @Value(value = "${installation.consumer.sasl.jaas.config}")
    private String consumerSaslJaasConfig;
    
    @Value(value = "${installation.consumer.sasl.mechanism}")
    private String consumerSaslMechanism;
    
	@Value(value = "${installation.consumer.topic.name}")
	private String consumerTopicName;
	
    @Value(value = "${installation.producer.bootstrapAddress}")
    private String producerBootstrapAddress;
    
    @Value(value = "${installation.producer.topic.name}")
    private String producerTopicName;
    
	@Value(value = "${installation.producer.security.protocol}")
	private String producerSecurityProtocol;
	
    @Value(value = "${installation.producer.sasl.jaas.config}")
    private String producerSaslJaasConfig;
    
    @Value(value = "${installation.producer.sasl.mechanism}")
    private String producerSaslMechanism;
    
    public ConsumerFactory<Integer, InstallationOutDTO> installationOutConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerBootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "client-installation-replicator");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put("security.protocol", consumerSecurityProtocol);
		props.put("sasl.mechanism", consumerSaslMechanism);
		props.put("sasl.jaas.config", consumerSaslJaasConfig);

		// NO DEJAR EN PRO, SOLO PARA PRUEBAS (Lee desde el primer mensaje)
//		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		
        return new DefaultKafkaConsumerFactory<>(props, new IntegerDeserializer(), new JsonDeserializer<>(InstallationOutDTO.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Integer, InstallationOutDTO> installationOutKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, InstallationOutDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(installationOutConsumerFactory());
        return factory;
    }

    @Bean
    public ProducerFactory<Integer, InstallationOutDTO> installationProducerFactory() {
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
    public KafkaTemplate<Integer, InstallationOutDTO> installationKafkaTemplate() {
        return new KafkaTemplate<>(installationProducerFactory());
    }
}

package com.cooba.core.kafka;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "stomp.kafka.enable", havingValue = "true")
public class KafkaConfig {

    @Bean
    @ConfigurationProperties(prefix = "stomp.kafka")
    public KafkaProperties kafkaProperties() {
        return new KafkaProperties();
    }
}

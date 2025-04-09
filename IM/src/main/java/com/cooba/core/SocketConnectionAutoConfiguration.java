package com.cooba.core;

import com.cooba.constant.StompMQ;
import com.cooba.core.kafka.KafkaStompSocketConnection;
import com.cooba.core.spring.StompSocketConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SocketConnectionAutoConfiguration {
    private final StompMQ stompMQ;

    @Bean
    @ConditionalOnMissingBean(SocketConnection.class)
    @ConditionalOnProperty(name = "stomp.kafka.enable", havingValue = "false", matchIfMissing = true)
    public SocketConnection defaultSocketConnection(SimpMessagingTemplate messagingTemplate) {
        return new StompSocketConnection(messagingTemplate);
    }

    @Bean
    @ConditionalOnProperty(name = "stomp.kafka.enable", havingValue = "true")
    public SocketConnection kafkaSocketConnection(SimpMessagingTemplate messagingTemplate, KafkaTemplate<String, byte[]> kafkaTemplate) {
        if (stompMQ.getEnable()) {
            log.error("stomp.mq.enable should set to false");
            throw new UnsupportedOperationException();
        }

        return new KafkaStompSocketConnection(messagingTemplate, kafkaTemplate);
    }
}

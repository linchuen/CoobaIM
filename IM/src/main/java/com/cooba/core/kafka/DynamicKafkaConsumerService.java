package com.cooba.core.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

//@Service
@RequiredArgsConstructor
public class DynamicKafkaConsumerService {
    private final  ConcurrentMessageListenerContainer<String, String> containers;

    public void subscribeToTopic(String topic) {

    }

    public void unsubscribeFromTopic(String topic) {

    }
}


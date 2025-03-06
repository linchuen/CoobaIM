package com.cooba.core.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Service
@RequiredArgsConstructor
public class DynamicKafkaConsumerService {
    private final Map<String, ConcurrentMessageListenerContainer<String, String>> listenerContainers = new ConcurrentHashMap<>();

    public void addConsumer(String topicName, String consumerGroup, MessageListener<String, String> messageListener) {
        if (listenerContainers.containsKey(topicName + consumerGroup)) return;

        ConsumerFactory<String, String> consumerFactory = createConsumerFactory(consumerGroup);
        ContainerProperties containerProperties = new ContainerProperties(topicName);
        containerProperties.setMessageListener(messageListener);

        ConcurrentMessageListenerContainer<String, String> container =
                new ConcurrentMessageListenerContainer<>(consumerFactory, containerProperties);
        container.start();
        listenerContainers.put(topicName + consumerGroup, container);

        System.out.println("已啟動 Consumer Group: " + consumerGroup + " 訂閱 Topic: " + topicName);
    }

    public void removeConsumer(String topicName, String consumerGroup) {
        ConcurrentMessageListenerContainer<String, String> container = listenerContainers.remove(topicName + consumerGroup);
        if (container != null) {
            container.stop();
            System.out.println("已停止 Consumer Group: " + topicName + " 訂閱 Topic: " + topicName);
        } else {
            System.out.println("Consumer Group " + consumerGroup + " 訂閱 Topic: " + topicName + " 不存在");
        }
    }

    private ConsumerFactory<String, String> createConsumerFactory(String consumerGroup) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup); // 動態設置 Consumer Group
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(config);
    }
}



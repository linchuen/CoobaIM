package com.cooba.core.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
@ConditionalOnProperty(name = "stomp.kafka.enable", havingValue = "true")
public class KafkaConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "stomp.kafka")
    @ConditionalOnProperty(name = "stomp.mq.enable", havingValue = "false")
    public KafkaProperties kafkaProperties() {
        return new KafkaProperties();
    }

    @Bean
    public KafkaAdmin.NewTopics topics() {
        List<NewTopic> groups = IntStream.range(0, 10)
                .boxed()
                .map(i ->
                        TopicBuilder.name("chat-room-" + i)
                                .partitions(10)
                                .replicas(1)
                                .build())
                .collect(Collectors.toList());

        List<NewTopic> users = IntStream.range(0, 10)
                .boxed()
                .map(i ->
                        TopicBuilder.name("chat-user-" + i)
                                .partitions(10)
                                .replicas(1)
                                .build())
                .toList();

        List<NewTopic> userEvents = IntStream.range(0, 10)
                .boxed()
                .map(i ->
                        TopicBuilder.name("chat-user-event-" + i)
                                .partitions(10)
                                .replicas(1)
                                .build())
                .toList();

        NewTopic allEvent = TopicBuilder.name("all-event")
                .partitions(10)
                .replicas(1)
                .build();
        NewTopic all = TopicBuilder.name("all")
                .partitions(10)
                .replicas(1)
                .build();
        groups.addAll(users);
        groups.addAll(userEvents);
        groups.add(allEvent);
        groups.add(all);

        NewTopic[] topics = groups.toArray(NewTopic[]::new);
        return new KafkaAdmin.NewTopics(topics);
    }
}

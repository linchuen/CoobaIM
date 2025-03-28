package com.cooba.core.kafka;

import com.cooba.constant.IMEvent;
import com.cooba.core.SocketConnection;
import com.cooba.entity.Chat;
import com.cooba.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Slf4j
@RequiredArgsConstructor
public class KafkaStompSocketConnection implements SocketConnection {
    private final SimpMessagingTemplate messagingTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void bindGroup(String userId, String group) {

    }

    @Override
    public void unbindGroup(String userId, String group) {

    }

    @Override
    public <T> void sendUserEvent(String userId, IMEvent event, T t) {
        String payload = JsonUtil.toJson(t);
        String topic = decideTopic(userId, "user-event");
        kafkaTemplate.send(topic, userId, payload);

        log.info("kafka topic:{} /queue/{} {} content:{}", topic, event, userId, event.getType() + "//" + payload);
    }

    @KafkaListener(topicPattern = "chat-user-event-.*")
    public void listenUserEvent(ConsumerRecord<String, String> record) {
        String userId = record.key();
        String[] records = record.value().split("//");
        String event = records[0];
        String payload = records[1];

        messagingTemplate.convertAndSendToUser(userId, "/queue/" + event, payload);
        log.info("/queue/{} {} content:{}", event, userId, payload);
    }

    @Override
    public <T> void sendAllEvent(IMEvent event, T t) {
        String payload = JsonUtil.toJson(t);
        kafkaTemplate.send("all-event", event.getType() + "//" + payload);
        log.info("kafka topic:{} /topic/{}  content:{}", "all-event", event, payload);
    }

    @KafkaListener(topics = "all-event")
    public void listenAllEvent(ConsumerRecord<String, String> record) {
        String[] records = record.value().split("//");
        String event = records[0];
        String payload = records[1];

        messagingTemplate.convertAndSend("/topic/" + event, payload);
        log.info("/topic/{}  content:{}", event, payload);
    }

    @Override
    public void sendToUser(String userId, Chat chat) {
        String payload = JsonUtil.toJson(chat);
        String topic = decideTopic(userId, "user");
        kafkaTemplate.send(topic, userId, payload);

        log.info("kafka topic:{} /private/{} content:{}", topic, userId, payload);
    }

    @KafkaListener(topicPattern = "chat-user-.*")
    public void listenUser(ConsumerRecord<String, String> record) {
        String userId = record.key();
        Chat chat = JsonUtil.fromJson(record.value(), Chat.class);

        messagingTemplate.convertAndSendToUser(userId, "/private", chat, buildHeader());
        log.info("/private/{} content:{}", userId, record.value());
    }

    @Override
    public void sendToGroup(String group, Chat chat) {
        String payload = JsonUtil.toJson(chat);
        String topic = decideTopic(group, "room");
        kafkaTemplate.send(topic, group, payload);

        log.info("kafka topic:{} /group/{} content:{}", topic, group, payload);
    }

    @KafkaListener(topicPattern = "chat-room-.*")
    public void listenGroup(ConsumerRecord<String, String> record) {
        String group = record.key();
        Chat chat = JsonUtil.fromJson(record.value(), Chat.class);

        messagingTemplate.convertAndSend("/group/" + group, chat, buildHeader());
        log.info("/group/{} content:{}", group, record.value());
    }

    @Override
    public void sendToAll(String message) {
        kafkaTemplate.send("all", message);
    }

    @KafkaListener(topics = "all")
    public void listenAll(ConsumerRecord<String, String> record) {
        messagingTemplate.convertAndSend("/topic/broadcast", record.value());
    }

    private MessageHeaders buildHeader() {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setHeader("contentType", "application/protobuf");
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }

    private String decideTopic(String key, String type) {
        return "chat-" + type + "-" + key.hashCode() % 10;
    }
}

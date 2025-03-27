package com.cooba.core.kafka;

import com.cooba.constant.IMEvent;
import com.cooba.core.SocketConnection;
import com.cooba.entity.Chat;
import com.cooba.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "stomp.kafka.enable", havingValue = "true")
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
        kafkaTemplate.send(decideTopic(userId, "user-event"), userId, payload);

        log.info("kafka /queue/{} {} content:{}", event, userId, event.getType() + "//" + payload);
    }

    @KafkaListener(topicPattern = "chat-user-event-*", groupId = "my-group")
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
        kafkaTemplate.send("all-event", event.getType()+ "//" +  payload);
        log.info("kafka /topic/{}  content:{}", event, payload);
    }

    @KafkaListener(topics = "all-event", groupId = "my-group")
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
        kafkaTemplate.send(decideTopic(userId, "user"), userId, payload);

        log.info("kafka /private/{} content:{}", userId, payload);
    }

    @KafkaListener(topicPattern = "chat-user-*", groupId = "my-group")
    public void listenUser(ConsumerRecord<String, String> record) {
        String userId = record.key();
        Chat chat = JsonUtil.fromJson(record.value(), Chat.class);

        messagingTemplate.convertAndSendToUser(userId, "/private", chat, buildHeader());
        log.info("/private/{} content:{}", userId, record.value());
    }

    @Override
    public void sendToGroup(String group, Chat chat) {
        String payload = JsonUtil.toJson(chat);
        kafkaTemplate.send(decideTopic(group, "room"), group, payload);

        log.info("kafka /group/{} content:{}", group, payload);
    }

    @KafkaListener(topicPattern = "chat-room-*", groupId = "my-group")
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

    @KafkaListener(topics = "all", groupId = "my-group")
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

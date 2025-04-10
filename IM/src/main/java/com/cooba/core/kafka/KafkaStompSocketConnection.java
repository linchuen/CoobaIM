package com.cooba.core.kafka;

import com.cooba.constant.IMEvent;
import com.cooba.core.SocketConnection;
import com.cooba.core.spring.ProtoMessageConverter;
import com.cooba.entity.Chat;
import com.cooba.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    @Override
    public void bindGroup(String userId, String group) {

    }

    @Override
    public void unbindGroup(String userId, String group) {

    }

    @Override
    public <T> void sendUserEvent(String userId, IMEvent event, T t) {
        EventData eventData = new EventData(event.getType(), JsonUtil.toJson(t));
        String payload = JsonUtil.toJson(eventData);
        String topic = decideTopic(userId, "user-event");
        kafkaTemplate.send(topic, userId, payload.getBytes());

        log.info("kafka topic:{} /queue/{} {} content:{}", topic, event, userId, payload);
    }

    @KafkaListener(topicPattern = "chat-user-event-.*")
    public void listenUserEvent(ConsumerRecord<String, byte[]> record) {
        String userId = record.key();
        EventData eventData = JsonUtil.fromJson(new String(record.value()), EventData.class);
        String event = eventData.event;
        String payload = eventData.payload;

        messagingTemplate.convertAndSendToUser(userId, "/queue/" + event, payload);
        log.info("/queue/{} {} content:{}", event, userId, payload);
    }

    @Override
    public <T> void sendAllEvent(IMEvent event, T t) {
        EventData eventData = new EventData(event.getType(), JsonUtil.toJson(t));
        String payload = JsonUtil.toJson(eventData);
        kafkaTemplate.send("all-event", payload.getBytes());

        log.info("kafka topic:{} /topic/{}  content:{}", "all-event", event, payload);
    }

    @KafkaListener(topics = "all-event")
    public void listenAllEvent(ConsumerRecord<String, byte[]> record) {
        EventData eventData = JsonUtil.fromJson(new String(record.value()), EventData.class);
        String event = eventData.event;
        String payload = eventData.payload;

        messagingTemplate.convertAndSend("/topic/" + event, payload);
        log.info("/topic/{}  content:{}", event, payload);
    }

    @Override
    public void sendToUser(String userId, Chat chat) {
        String topic = decideTopic(userId, "user");
        kafkaTemplate.send(topic, userId, ProtoMessageConverter.buildChatProto(chat));

        String payload = JsonUtil.toJson(chat);
        log.info("kafka topic:{} /private/{} content:{}", topic, userId, payload);
    }

    @KafkaListener(topicPattern = "chat-user-.*")
    public void listenUser(ConsumerRecord<String, byte[]> record) {
        String userId = record.key();

        messagingTemplate.convertAndSendToUser(userId, "/private", record.value(), buildHeader());
        log.info("/private/{} content:{}", userId, record.value());
    }

    @Override
    public void sendToGroup(String group, Chat chat) {
        String topic = decideTopic(group, "room");
        kafkaTemplate.send(topic, group, ProtoMessageConverter.buildChatProto(chat));

        String payload = JsonUtil.toJson(chat);
        log.info("kafka topic:{} /group/{} content:{}", topic, group, payload);
    }

    @KafkaListener(topicPattern = "chat-room-.*")
    public void listenGroup(ConsumerRecord<String, byte[]> record) {
        String group = record.key();

        messagingTemplate.convertAndSend("/group/" + group, record.value(), buildHeader());
        log.info("/group/{} content:{}", group, record.value());
    }

    @Override
    public void sendToAll(String message) {
        kafkaTemplate.send("all", message.getBytes());
    }

    @KafkaListener(topics = "all")
    public void listenAll(ConsumerRecord<String, byte[]> record) {
        messagingTemplate.convertAndSend("/topic/broadcast", new String(record.value()));
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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class EventData {
        private String event;
        private String payload;
    }

}

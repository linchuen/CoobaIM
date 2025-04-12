package com.cooba.core.kafka;

import com.cooba.constant.IMEvent;
import com.cooba.core.SocketConnection;
import com.cooba.core.spring.ProtoMessageConverter;
import com.cooba.entity.Chat;
import com.cooba.proto.ChatProto;
import com.cooba.util.JsonUtil;
import com.cooba.util.KryoUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Slf4j
public class KafkaStompSocketConnection implements SocketConnection {
    private final SimpMessagingTemplate messagingTemplate;
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public KafkaStompSocketConnection(SimpMessagingTemplate messagingTemplate, KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.kafkaTemplate = kafkaTemplate;
        KryoUtil.register(EventData.class);
    }

    @Override
    public void bindGroup(String userId, String group) {

    }

    @Override
    public void unbindGroup(String userId, String group) {

    }

    @Override
    public <T> void sendUserEvent(String userId, IMEvent event, T t) {
        String destination = "/queue/" + event.getType();
        EventData eventData = new EventData(destination, JsonUtil.toJson(t));
        String topic = decideTopic(userId, "user-event");
        kafkaTemplate.send(topic, userId, KryoUtil.write(eventData));

        logKafkaEvent(destination, eventData);
    }

    @KafkaListener(topicPattern = "chat-user-event-.*")
    public void listenUserEvent(ConsumerRecord<String, byte[]> record) {
        String userId = record.key();
        EventData eventData = KryoUtil.read(record.value(), EventData.class);
        messagingTemplate.convertAndSendToUser(userId, eventData.destination, eventData.payload);

        logStompEvent(eventData);
    }

    @Override
    public <T> void sendAllEvent(IMEvent event, T t) {
        String destination = "/topic/" + event.getType();
        EventData eventData = new EventData(destination, JsonUtil.toJson(t));
        kafkaTemplate.send("all-event", KryoUtil.write(eventData));

        logKafkaEvent(destination, eventData);
    }

    @KafkaListener(topics = "all-event")
    public void listenAllEvent(ConsumerRecord<String, byte[]> record) {
        EventData eventData = KryoUtil.read(record.value(), EventData.class);
        messagingTemplate.convertAndSend(eventData.destination, eventData.payload);

        logStompEvent(eventData);
    }

    @Override
    public void sendToUser(String userId, Chat chat) {
        String topic = decideTopic(userId, "user");
        kafkaTemplate.send(topic, userId, ProtoMessageConverter.buildChatProto(chat));

        logKafkaChat(topic, "/private/" + userId, chat);
    }

    @KafkaListener(topicPattern = "chat-user-.*")
    public void listenUser(ConsumerRecord<String, byte[]> record) {
        String userId = record.key();
        messagingTemplate.convertAndSendToUser(userId, "/private", record.value(), buildHeader());

        logStompChat("/private/" + userId, record.value());
    }

    @Override
    public void sendToGroup(String group, Chat chat) {
        String topic = decideTopic(group, "room");
        kafkaTemplate.send(topic, group, ProtoMessageConverter.buildChatProto(chat));

        logKafkaChat(topic, "/group/" + group, chat);
    }

    @KafkaListener(topicPattern = "chat-room-.*")
    public void listenGroup(ConsumerRecord<String, byte[]> record) {
        String group = record.key();
        messagingTemplate.convertAndSend("/group/" + group, record.value(), buildHeader());

        logStompChat("/group/" + group, record.value());
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
    public static class EventData {
        private String destination;
        private String payload;
    }

    private void logKafkaEvent(String topic, EventData eventData) {
        log.info("kafka topic:{} destination:{}  content:{}", topic, eventData.destination, eventData.payload);
    }

    private void logStompEvent(EventData eventData) {
        log.info("destination:{}  content:{}", eventData.destination, eventData.payload);
    }

    private void logKafkaChat(String topic, String destination, Chat chat) {
        log.info("kafka topic:{} destination:{} chatId:{} uuid:{} room:{} user:{} time:{}",
                topic,
                destination,
                chat.getId(),
                chat.getUuid(),
                chat.getRoomId(),
                chat.getUserId(),
                chat.getCreatedTime()
        );
    }

    private void logStompChat(String destination, byte[] bytes) {
        ChatProto.ChatInfo chatInfo;
        try {
            chatInfo = ProtoMessageConverter.readChatProto(bytes);
            log.info("destination:{} chatId:{} uuid:{} room:{} user:{} time:{}",
                    destination,
                    chatInfo.getId(),
                    chatInfo.getUuid(),
                    chatInfo.getRoomId(),
                    chatInfo.getUserId(),
                    chatInfo.getCreatedTime()
            );
        } catch (InvalidProtocolBufferException e) {
            log.error("parse ProtoBuf error",e);
        }
    }
}

package com.cooba.core.kafka;

import com.cooba.constant.EventEnum;
import com.cooba.core.SocketConnection;
import com.cooba.entity.Chat;
import com.cooba.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Slf4j
//@Component
@RequiredArgsConstructor
public class StompSocketConnection implements SocketConnection {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void bindGroup(String userId, String group) {

    }

    @Override
    public void unbindGroup(String userId, String group) {

    }

    @Override
    public <T> void sendUserEvent(String userId, EventEnum event, T t) {
        String payload = JsonUtil.toJson(t);
        messagingTemplate.convertAndSendToUser(userId, "/queue/" + event.getType(), payload);
        log.info("/queue/{} {} content:{}", event, userId, payload);
    }

    public MessageListener<String, String> kafkaHandleSendUserEvent() {
        return data -> {
            String topic = data.topic();
            String[] strings = topic.split("-");
            String userId = strings[0];
            String payload = data.value();
            messagingTemplate.convertAndSendToUser(userId, strings[1], payload);
        };
    }

    @Override
    public void sendToUser(String userId, Chat chat) {
        String payload = JsonUtil.toJson(chat);
        messagingTemplate.convertAndSendToUser(userId, "/private", payload);
        log.info("/private/{} content:{}", userId, payload);
    }

    public MessageListener<String, String> kafkaHandleSendToUser() {
        return data -> {
            String topic = data.topic();
            String[] strings = topic.split("-");
            String userId = strings[0];
            String payload = data.value();
            messagingTemplate.convertAndSendToUser(userId, strings[1], payload);
        };
    }

    @Override
    public void sendToGroup(String group, Chat chat) {
        String payload = JsonUtil.toJson(chat);

        kafkaTemplate.send("/group/" + group, payload);
        log.info("/group/{} content:{}", group, payload);
    }

    public MessageListener<String, String> kafkaHandleSendToGroup() {
        return data -> {
            String topic = data.topic();
            String payload = data.value();
            messagingTemplate.convertAndSend(topic, payload);
            log.info("{} content:{}", topic, payload);
        };
    }

    @Override
    public void sendToAll(String message) {
        messagingTemplate.convertAndSend("/topic/broadcast", message);
    }
}

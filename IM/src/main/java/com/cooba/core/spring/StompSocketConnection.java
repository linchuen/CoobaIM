package com.cooba.core.spring;

import com.cooba.constant.IMEvent;
import com.cooba.core.SocketConnection;
import com.cooba.entity.Chat;
import com.cooba.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompSocketConnection implements SocketConnection {
    private final SimpMessagingTemplate messagingTemplate;
    private final Map<String, Object> headers = Map.of("contentType", "application/protobuf");

    @Override
    public void bindGroup(String userId, String group) {

    }

    @Override
    public void unbindGroup(String userId, String group) {

    }

    @Override
    public <T> void sendUserEvent(String userId, IMEvent event, T t) {
        String payload = JsonUtil.toJson(t);
        messagingTemplate.convertAndSendToUser(userId, "/queue/" + event.getType(), payload);
        log.info("/queue/{} {} content:{}", event, userId, payload);
    }

    @Override
    public <T> void sendAllEvent(IMEvent event, T t) {
        String payload = JsonUtil.toJson(t);
        messagingTemplate.convertAndSend("/topic/" + event.getType(), payload);
        log.info("/topic/{}  content:{}", event, payload);
    }

    @Override
    public void sendToUser(String userId, Chat chat) {
        String payload = JsonUtil.toJson(chat);
        messagingTemplate.convertAndSendToUser(userId, "/private", payload, headers);
        log.info("/private/{} content:{}", userId, payload);
    }

    @Override
    public void sendToGroup(String group, Chat chat) {
        String payload = JsonUtil.toJson(chat);
        messagingTemplate.convertAndSend("/group/" + group, payload, headers);
        log.info("/group/{} content:{}", group, payload);
    }

    @Override
    public void sendToAll(String message) {
        messagingTemplate.convertAndSend("/topic/broadcast", message);
    }
}

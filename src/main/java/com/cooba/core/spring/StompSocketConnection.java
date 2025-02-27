package com.cooba.core.spring;

import com.cooba.core.SocketConnection;
import com.cooba.entity.Chat;
import com.cooba.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompSocketConnection implements SocketConnection {
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void bindGroup(String userId, String group) {

    }

    @Override
    public void unbindGroup(String userId, String group) {

    }

    @Override
    public <T> void sendUserEvent(String userId, String event, T t) {
        String payload = JsonUtil.toJson(t);
        messagingTemplate.convertAndSendToUser(userId, "/queue/" + event, payload);
        log.debug("/queue/{} {} content:{}", event, userId, payload);
    }

    @Override
    public void sendToUser(String userId, Chat chat) {
        String payload = JsonUtil.toJson(chat);
        messagingTemplate.convertAndSendToUser(userId, "/private", payload);
        log.debug("/private/{} content:{}", userId, payload);
    }

    @Override
    public void sendToGroup(String group, Chat chat) {
        String payload = JsonUtil.toJson(chat);
        messagingTemplate.convertAndSend("/group/" + group, payload);
        log.debug("/group/{} content:{}", group, payload);
    }

    @Override
    public void sendToAll(String message) {
        messagingTemplate.convertAndSend("/topic/broadcast", message);
    }
}

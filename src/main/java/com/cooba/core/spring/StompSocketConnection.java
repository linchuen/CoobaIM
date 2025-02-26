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
    public void bindGroup(String userid, String group) {

    }

    @Override
    public void unbindGroup(String userid, String group) {

    }

    @Override
    public void sendUserEvent(String userid, String event) {
        messagingTemplate.convertAndSendToUser(userid, "/private", event);
    }

    @Override
    public void sendToUser(String userid, Chat chat) {
        String payload = JsonUtil.toJson(chat);
        messagingTemplate.convertAndSendToUser(userid, "/private", payload);
        log.debug("/private/{} content:{}", userid, payload);
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

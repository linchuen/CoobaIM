package com.cooba.core.spring;

import com.cooba.core.SocketConnection;
import com.cooba.entity.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

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
    public void sendToUser(String userid, Chat chat) {
        messagingTemplate.convertAndSendToUser(userid, "/private",  chat);
    }

    @Override
    public void sendToGroup(String group, Chat chat) {
        messagingTemplate.convertAndSend("/group/" + group, chat);
    }

    @Override
    public void sendToAll(String message) {
        messagingTemplate.convertAndSend("/topic/broadcast",  message);
    }
}

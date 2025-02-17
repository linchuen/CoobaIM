package com.cooba.core.spring;

import com.cooba.core.SocketConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;


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
    public void sendToUser(String userid, String message) {
        messagingTemplate.convertAndSendToUser(userid, "/queue/messages",  message);
    }

    @Override
    public void sendToGroup(String group, String message) {
        messagingTemplate.convertAndSend("/group/" + group, message);
    }

    @Override
    public void sendToAll(String message) {
        messagingTemplate.convertAndSend("/topic/messages",  message);
    }
}

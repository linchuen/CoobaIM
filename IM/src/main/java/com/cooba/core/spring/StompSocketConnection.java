package com.cooba.core.spring;

import com.cooba.constant.IMEvent;
import com.cooba.core.SocketConnection;
import com.cooba.entity.Chat;
import com.cooba.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Slf4j
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
        messagingTemplate.convertAndSendToUser(userId, "/private", chat, buildHeader());
        String payload = JsonUtil.toJson(chat);
        log.info("/private/{} content:{}", userId, payload);
    }

    @Override
    public void sendToGroup(String group, Chat chat) {
        messagingTemplate.convertAndSend("/group/" + group, chat, buildHeader());
        String payload = JsonUtil.toJson(chat);
        log.info("/group/{} content:{}", group, payload);
    }

    @Override
    public void sendToAll(String message) {
        messagingTemplate.convertAndSend("/topic/broadcast", message);
    }

    private MessageHeaders buildHeader(){
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setHeader("contentType","application/protobuf");
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}

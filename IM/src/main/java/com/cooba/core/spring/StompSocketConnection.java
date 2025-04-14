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
        String destination = "/queue/" + event.getType();
        String payload = JsonUtil.toJson(t);

        messagingTemplate.convertAndSendToUser(userId, destination, payload);
        logEvent(destination, payload);
    }

    @Override
    public <T> void sendAllEvent(IMEvent event, T t) {
        String destination = "/topic/" + event.getType();
        String payload = JsonUtil.toJson(t);

        messagingTemplate.convertAndSend(destination, payload);
        logEvent(destination, payload);
    }

    @Override
    public void sendToUser(String userId, Chat chat) {
        String destination = "/private";

        messagingTemplate.convertAndSendToUser(userId, destination, chat, buildHeader());
        logChat(destination, chat);
    }

    @Override
    public void sendToGroup(String group, Chat chat) {
        String destination = "/group/" + group;

        messagingTemplate.convertAndSend(destination, chat, buildHeader());
        logChat(destination, chat);
    }

    @Override
    public void sendToAll(String message) {
        String destination = "/topic/broadcast";
        messagingTemplate.convertAndSend(destination, message);
    }

    private MessageHeaders buildHeader() {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setHeader("contentType", "application/octet-stream");
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }

    private void logChat(String destination, Chat chat) {
        log.info("destination:{} chatId:{} uuid:{} room:{} user:{} time:{}",
                destination,
                chat.getId(),
                chat.getUuid(),
                chat.getRoomId(),
                chat.getUserId(),
                chat.getCreatedTime()
        );
    }

    private void logEvent(String destination, String content) {
        log.info("destination:{} content:{}", destination, content);
    }
}

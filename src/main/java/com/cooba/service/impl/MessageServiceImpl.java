package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.dto.NotifyMessage;
import com.cooba.dto.SendMessage;
import com.cooba.entity.Chat;
import com.cooba.entity.Notification;
import com.cooba.entity.RoomUser;
import com.cooba.repository.ChatRepository;
import com.cooba.repository.NotificationRepository;
import com.cooba.service.MessageService;
import com.cooba.tio.TioWebSocketServerBootstrap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final ChatRepository chatRepository;
    private final NotificationRepository notificationRepository;
    private final TioWebSocketServerBootstrap bootstrap;
    @Override
    public void sendToUser(SendMessage message) {
        Chat chat = new Chat();
        BeanUtils.copyProperties(message, chat);

        chatRepository.insert(chat);

//        Tio.sendToUser(bootstrap.getServerTioConfig(), request.getUid(), WsResponse.fromText(request.getMsg(), StandardCharsets.UTF_8.name()));
    }

    @Override
    public void sendToRoom(SendMessage message) {
        Chat chat = new Chat();
        BeanUtils.copyProperties(message, chat);

        chatRepository.insert(chat);

//        Tio.sendToGroup(bootstrap.getServerTioConfig(), request.getGroupId(), WsResponse.fromText(request.getMsg(), StandardCharsets.UTF_8.name()));
    }

    @Override
    public void sendToAll(NotifyMessage message) {
        notificationRepository.insert(new Notification());

//        Tio.sendToAll(bootstrap.getServerTioConfig(), WsResponse.fromText(request.getMsg(), StandardCharsets.UTF_8.name()));
    }
}

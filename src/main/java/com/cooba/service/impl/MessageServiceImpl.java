package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.entity.Chat;
import com.cooba.entity.Notification;
import com.cooba.repository.ChatRepository;
import com.cooba.repository.NotificationRepository;
import com.cooba.service.MessageService;
import com.cooba.tio.TioWebSocketServerBootstrap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final ChatRepository chatRepository;
    private final NotificationRepository notificationRepository;
    private final TioWebSocketServerBootstrap bootstrap;
    @Override
    public void sendToUser() {
        chatRepository.insert(new Chat());

//        Tio.sendToUser(bootstrap.getServerTioConfig(), request.getUid(), WsResponse.fromText(request.getMsg(), StandardCharsets.UTF_8.name()));
    }

    @Override
    public void sendToRoom() {
        chatRepository.insert(new Chat());

//        Tio.sendToGroup(bootstrap.getServerTioConfig(), request.getGroupId(), WsResponse.fromText(request.getMsg(), StandardCharsets.UTF_8.name()));
    }

    @Override
    public void sendToAll() {
        notificationRepository.insert(new Notification());

//        Tio.sendToAll(bootstrap.getServerTioConfig(), WsResponse.fromText(request.getMsg(), StandardCharsets.UTF_8.name()));
    }
}

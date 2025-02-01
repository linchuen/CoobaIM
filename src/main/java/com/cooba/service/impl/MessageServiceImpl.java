package com.cooba.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.BehaviorLayer;
import com.cooba.core.SocketConnection;
import com.cooba.dto.NotifyMessage;
import com.cooba.dto.SendMessage;
import com.cooba.entity.Chat;
import com.cooba.entity.Notification;
import com.cooba.repository.ChatRepository;
import com.cooba.repository.NotificationRepository;
import com.cooba.service.MessageService;
import com.cooba.core.tio.TioWebSocketServerBootstrap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final ChatRepository chatRepository;
    private final NotificationRepository notificationRepository;
    private final SocketConnection socketConnection;

    @Override
    public void sendToUser(SendMessage message) {
        Chat chat = new Chat(message);

        chatRepository.insert(chat);

        socketConnection.sendToUser(String.valueOf(chat.getUserId()), chat.getMessage());
    }

    @Override
    public void sendToRoom(SendMessage message) {
        Chat chat = new Chat(message);

        chatRepository.insert(chat);

        socketConnection.sendToGroup(String.valueOf(chat.getRoomId()), chat.getMessage());
    }

    @Override
    public void sendToAll(NotifyMessage message) {
        Notification notification = new Notification();
        notification.setUserId(message.getUserId());
        notification.setMessage(message.getMessage());

        notificationRepository.insert(notification);

        socketConnection.sendToAll(notification.getMessage());
    }

    @Override
    public List<Chat> getRoomChats(long roomId) {
        return chatRepository.selectList(new LambdaQueryWrapper<Chat>()
                .eq(Chat::getRoomId, roomId));
    }

    @Override
    public List<Notification> getNotifications() {
        return notificationRepository.selectList(new LambdaQueryWrapper<>());
    }
}

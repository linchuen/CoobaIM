package com.cooba.service;

import com.cooba.dto.NotifyMessage;
import com.cooba.dto.SendMessage;
import com.cooba.entity.Chat;
import com.cooba.entity.Notification;

import java.util.List;

public interface MessageService {
    void sendToUser(SendMessage message);

    void sendToRoom(SendMessage message);

    void sendToAll(NotifyMessage message);

    List<Chat> getRoomChats(long roomId);

    List<Notification> getNotifications();
}

package com.cooba.service;

import com.cooba.dto.NotifyMessage;
import com.cooba.dto.SendMessage;

public interface MessageService {
    void sendToUser(SendMessage message);

    void sendToRoom(SendMessage message);

    void sendToAll(NotifyMessage message);
}

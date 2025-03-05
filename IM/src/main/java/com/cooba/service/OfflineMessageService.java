package com.cooba.service;

import com.cooba.entity.Chat;

public interface OfflineMessageService {

    void sendToUser(String userId, Chat chat);

    void sendToGroup(String group, Chat chat);

    void sendToAll(String message);
}

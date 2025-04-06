package com.cooba.service;

import com.cooba.dto.SendMessage;

public interface OfflineMessageService {

    void sendOffline(long currentUserId, SendMessage message);


    void sendToAll(String message);
}

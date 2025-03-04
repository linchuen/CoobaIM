package com.cooba.core;

import com.cooba.entity.Chat;

import java.util.List;
import java.util.function.Supplier;

public interface OfflineMessageService {

    void sendToUser(String userId, Chat chat);

    void sendToGroup(Supplier<List<String>> userIds, Chat chat);

    void sendToAll(String message);
}

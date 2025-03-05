package com.cooba.service.impl;

import com.cooba.entity.Chat;
import com.cooba.service.OfflineMessageService;
import com.cooba.util.ConnectionManager;
import com.cooba.util.JsonUtil;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FcmOfflineMessageService implements OfflineMessageService {
    private final ConnectionManager connectionManager;

    @Async
    @Override
    public void sendToUser(String userId, Chat chat) {
        boolean online = connectionManager.isUserOnline(userId);
        if (online) return;

        FirebaseMessaging messaging = FirebaseMessaging.getInstance();
        Message message = Message.builder()
                .putData("body", JsonUtil.toJson(chat))
                .setToken("token")
                .build();

        try {
            messaging.send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

    @Async
    @Override
    public void sendToGroup(String group, Chat chat) {
        List<String> userIds = connectionManager.getGroupMembers(group)
                .keySet()
                .stream()
                .map(object -> (String) object)
                .toList();

        for (String userId : userIds) {
            boolean online = connectionManager.isUserOnline(userId);
            if (online) continue;

            FirebaseMessaging messaging = FirebaseMessaging.getInstance();
            Message message = Message.builder()
                    .putData("body", JsonUtil.toJson(chat))
                    .setToken("token")
                    .build();

            try {
                messaging.send(message);
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendToAll(String message) {

    }
}

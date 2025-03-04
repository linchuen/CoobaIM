package com.cooba.core.spring;

import com.cooba.core.OfflineMessageService;
import com.cooba.entity.Chat;
import com.cooba.util.JsonUtil;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class FcmOfflineMessageService implements OfflineMessageService {
    private final WebSocketSessionManager webSocketSessionManager;

    @Async
    @Override
    public void sendToUser(String userId, Chat chat) {
        boolean online = webSocketSessionManager.isUserOnline(userId);
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
    public void sendToGroup(Supplier<List<String>> userIdSupplier, Chat chat) {
        List<String> userIds = userIdSupplier.get();

        for (String userId : userIds) {
            boolean online = webSocketSessionManager.isUserOnline(userId);
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

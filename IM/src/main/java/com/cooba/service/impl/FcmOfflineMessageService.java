package com.cooba.service.impl;

import com.cooba.entity.Chat;
import com.cooba.repository.RoomUserRepository;
import com.cooba.service.OfflineMessageService;
import com.cooba.util.ConnectionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FcmOfflineMessageService implements OfflineMessageService {
    private final ConnectionManager connectionManager;
    private final RoomUserRepository roomUserRepository;

    @Async
    @Override
    public void sendToUser(String userId, Chat chat) {
        boolean online = connectionManager.isUserOnline(userId);
        if (online) return;

    }

    @Async
    @Override
    public void sendToGroup(String group, Chat chat) {
        List<String> userIds = roomUserRepository.find(Long.parseLong(group))
                .stream()
                .map(roomUser -> String.valueOf(roomUser.getUserId()))
                .toList();

        for (String userId : userIds) {
            boolean online = connectionManager.isUserOnline(userId);
            if (online) continue;
        }
    }

    @Override
    public void sendToAll(String message) {

    }
}

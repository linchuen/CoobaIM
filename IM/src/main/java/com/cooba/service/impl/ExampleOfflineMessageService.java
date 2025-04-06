package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.dto.SendMessage;
import com.cooba.entity.RoomUser;
import com.cooba.repository.RoomUserRepository;
import com.cooba.service.OfflineMessageService;
import com.cooba.util.ConnectionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class ExampleOfflineMessageService implements OfflineMessageService {
    private final ConnectionManager connectionManager;
    private final RoomUserRepository roomUserRepository;

    @Async
    @Override
    public void sendOffline(long currentUserId, SendMessage message) {
        List<RoomUser> roomUsers = roomUserRepository.find(message.getRoomId());
        roomUsers.stream()
                .filter(roomUser -> roomUser.getUserId() != currentUserId)
                .filter(roomUser -> !connectionManager.isUserOnline(String.valueOf(roomUser.getUserId())))
                .forEach(roomUser -> log.info("send offline message to {}: {}", roomUser.getUserId(), message.getMessage()));
    }

    @Override
    public void sendToAll(String message) {

    }
}

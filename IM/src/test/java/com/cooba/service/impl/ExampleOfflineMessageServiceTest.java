package com.cooba.service.impl;

import com.cooba.annotation.MybatisLocalTest;
import com.cooba.aop.UserThreadLocal;
import com.cooba.dto.SendMessage;
import com.cooba.entity.RoomUser;
import com.cooba.repository.RoomUserRepository;
import com.cooba.util.ConnectionManager;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;

@MybatisLocalTest
@ContextConfiguration(classes = {ExampleOfflineMessageService.class})
@Sql(scripts = {"/sql/RoomUser-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = {"/delete/RoomUser-delete.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class ExampleOfflineMessageServiceTest {
    @Autowired
    ExampleOfflineMessageService offlineMessageService;
    @MockitoBean
    ConnectionManager connectionManager;
    @MockitoBean
    UserThreadLocal userThreadLocal;
    @Autowired
    RoomUserRepository roomUserRepository;

    @Test
    @DisplayName("測試離線發送執行")
    void sendOffline() {
        long roomId = 1L;
        Mockito.when(userThreadLocal.getCurrentUserId()).thenReturn(1L);
        Mockito.when(connectionManager.isUserOnline(anyString())).thenReturn(false);

        List<RoomUser> roomUsers = Instancio.createList(RoomUser.class);
        roomUsers.forEach(roomUser -> roomUser.setRoomId(roomId));
        roomUserRepository.insert(roomUsers);

        SendMessage sendMessage = Instancio.create(SendMessage.class);
        sendMessage.setRoomId(roomId);
        offlineMessageService.sendOffline(userThreadLocal.getCurrentUserId(), sendMessage);

        Mockito.verify(connectionManager, Mockito.times(roomUsers.size())).isUserOnline(anyString());
    }
}
package com.cooba.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.MybatisLocalTest;
import com.cooba.core.SocketConnection;
import com.cooba.dto.NotifyMessage;
import com.cooba.dto.SendMessage;
import com.cooba.entity.Chat;
import com.cooba.entity.Notification;
import com.cooba.repository.ChatRepository;
import com.cooba.repository.NotificationRepository;
import com.cooba.service.MessageService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@Rollback(value = false)
@MybatisLocalTest
@ContextConfiguration(classes = {MessageServiceImpl.class})
@Sql(scripts = {"/sql/Chat-schema.sql", "/sql/Notification-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class MessageServiceImplTest {
    @Autowired
    MessageService messageService;
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    NotificationRepository notificationRepository;
    @MockitoBean
    SocketConnection socketConnection;


    @Test
    void sendToUser() {
        SendMessage sendMessage = Instancio.create(SendMessage.class);
        messageService.sendToUser(sendMessage);

        List<String> chats = chatRepository.selectList(new LambdaQueryWrapper<Chat>()
                        .eq(Chat::getUserId, sendMessage.getUser().getId())
                        .eq(Chat::getRoomId, sendMessage.getRoomId()))
                .stream()
                .map(Chat::getMessage)
                .toList();

        boolean hasSend = chats.contains(sendMessage.getMessage());
        Assertions.assertTrue(hasSend);
    }

    @Test
    void sendToRoom() {
        SendMessage sendMessage = Instancio.create(SendMessage.class);
        messageService.sendToRoom(sendMessage);

        List<String> chats = chatRepository.selectList(new LambdaQueryWrapper<Chat>()
                        .eq(Chat::getUserId, sendMessage.getUser().getId())
                        .eq(Chat::getRoomId, sendMessage.getRoomId()))
                .stream()
                .map(Chat::getMessage)
                .toList();

        boolean hasSend = chats.contains(sendMessage.getMessage());
        Assertions.assertTrue(hasSend);
    }

    @Test
    void sendToAll() {
        NotifyMessage notifyMessage = Instancio.create(NotifyMessage.class);
        messageService.sendToAll(notifyMessage);

        List<String> chats = notificationRepository.selectList(new LambdaQueryWrapper<Notification>()
                        .eq(Notification::getUserId, notifyMessage.getUserId()))
                .stream()
                .map(Notification::getMessage)
                .toList();

        boolean hasSend = chats.contains(notifyMessage.getMessage());
        Assertions.assertTrue(hasSend);
    }
}
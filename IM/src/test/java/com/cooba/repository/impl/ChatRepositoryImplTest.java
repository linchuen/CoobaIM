package com.cooba.repository.impl;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.cooba.entity.Chat;
import com.cooba.entity.FriendApply;
import com.cooba.repository.ChatRepository;
import com.cooba.service.impl.FriendServiceImpl;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@Rollback(value = false)
@ActiveProfiles(value = "ck")
@MapperScan({"com.cooba.mapper"})
@ContextConfiguration(classes = {ChatRepositoryImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisPlusTest
class ChatRepositoryImplTest {
    @Autowired
    ChatRepository chatRepository;

    @Test
    void insert() {
        Chat chat = Instancio.create(Chat.class);
        chat.setId(null);
        chatRepository.insert(chat);
    }

    @Test
    void testInsert() {
    }

    @Test
    void selectById() {
    }

    @Test
    void selectByIds() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findChatByRoomId() {
    }
}
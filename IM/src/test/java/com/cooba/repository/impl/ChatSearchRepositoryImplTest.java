package com.cooba.repository.impl;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.cooba.config.MockConfig;
import com.cooba.entity.Chat;
import com.cooba.entity.ChatSearch;
import com.cooba.repository.ChatRepository;
import com.cooba.repository.ChatSearchRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@Disabled
@Rollback(value = false)
@ActiveProfiles(value = "ck")
@MapperScan({"com.cooba.mapper"})
@ContextConfiguration(classes = {ChatSearchRepositoryImpl.class, MockConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisPlusTest
class ChatSearchRepositoryImplTest {
    @Autowired
    ChatSearchRepository chatSearchRepository;

    @Test
    void insert() {
        ChatSearch chatSearch = Instancio.create(ChatSearch.class);
        chatSearch.setId(null);
        chatSearchRepository.insert(chatSearch);
        Assertions.assertNotNull(chatSearch.getId());
    }
}
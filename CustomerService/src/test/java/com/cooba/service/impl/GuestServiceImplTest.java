package com.cooba.service.impl;

import com.cooba.annotation.MybatisLocalTest;
import com.cooba.service.GuestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

@MybatisLocalTest
@ContextConfiguration(classes = {GuestServiceImpl.class})
@Sql(scripts = {"/sql/Guest-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class GuestServiceImplTest {
    @Autowired
    GuestService guestService;

    @Test
    @DisplayName("建立訪客用戶關係")
    void create() {
        long userId = 1;
        guestService.create(userId);
    }

    @Test
    @DisplayName("取得隨機訪客")
    void getRandomGuest() {
        for (int i = 1; i <= 10; i++) {
            guestService.create(i);
        }

        for (int i = 0; i < 10; i++) {
            long guestId = guestService.getRandomGuest();
            Assertions.assertTrue(guestId >= 1 && guestId <= 10);
        }
    }
}
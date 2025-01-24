package com.cooba.service.impl;

import com.cooba.annotation.MybatisLocalTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@Rollback(value = false)
@MybatisLocalTest
@ContextConfiguration(classes = {FriendServiceImpl.class})
class FriendServiceImplTest {

    @Test
    void apply() {
    }

    @Test
    void bind() {
    }

    @Test
    void unbind() {
    }
}
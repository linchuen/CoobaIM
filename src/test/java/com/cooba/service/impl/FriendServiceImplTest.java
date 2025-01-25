package com.cooba.service.impl;

import com.cooba.annotation.MybatisLocalTest;
import com.cooba.entity.Friend;
import com.cooba.service.FriendService;
import org.instancio.Instancio;
import org.instancio.InstancioApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@Rollback(value = false)
@MybatisLocalTest
@ContextConfiguration(classes = {FriendServiceImpl.class})
class FriendServiceImplTest {
    @Autowired
    FriendService friendService;

    @Test
    void apply() {
        Friend friend = Instancio.create(Friend.class);
        friendService.apply(friend);
    }

    @Test
    void bind() {
    }

    @Test
    void unbind() {
    }
}
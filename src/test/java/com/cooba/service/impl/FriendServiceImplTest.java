package com.cooba.service.impl;

import com.cooba.annotation.MybatisLocalTest;
import com.cooba.entity.FriendApply;
import com.cooba.service.FriendService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

@Rollback(value = false)
@MybatisLocalTest
@ContextConfiguration(classes = {FriendServiceImpl.class})
@Sql("/sql/Friend-schema.sql")
class FriendServiceImplTest {
    @Autowired
    FriendService friendService;

    @Test
    void apply() {
        FriendApply friendApply = Instancio.create(FriendApply.class);
        friendService.apply(friendApply);
    }

    @Test
    void bind() {
    }

    @Test
    void unbind() {
    }
}
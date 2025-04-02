package com.cooba.service.impl;

import com.cooba.annotation.MybatisLocalTest;
import com.cooba.constant.JwtSecret;
import com.cooba.entity.Session;
import com.cooba.entity.User;
import com.cooba.exception.BaseException;
import com.cooba.repository.SessionRepository;
import com.cooba.service.SessionService;
import com.cooba.util.JwtUtil;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

@MybatisLocalTest
@ContextConfiguration(classes = {SessionServiceImpl.class, JwtUtil.class, JwtSecret.class})
@Sql(scripts = {"/sql/Session-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = {"/sql/Session-delete.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class SessionServiceImplTest {
    @Autowired
    SessionService sessionService;
    @Autowired
    SessionRepository sessionRepository;

    @Test
    @DisplayName("建立session")
    void add() {
        User user = Instancio.create(User.class);
        sessionService.add(user, "web", "");

        Session session = sessionService.getInfo(user.getId(), "");
        Assertions.assertNotNull(session);
        Assertions.assertTrue(session.getEnable());
    }

    @Test
    @DisplayName("測試失效token刷新session")
    void addWithInvalidToken() {
        String token = "invalid token";
        User user = Instancio.create(User.class);

        Assertions.assertThrows(BaseException.class, () -> sessionService.refresh(user, token, "web", ""));
    }

    @Test
    @DisplayName("刷新session")
    void addWithRefreshToken() {
        User user = Instancio.create(User.class);
        Session session = sessionService.add(user, "web", "");

        Session added = sessionService.refresh(user, session.getToken(), "web", "");

        Assertions.assertNotEquals(added.getToken(), session.getToken());
    }

    @Test
    @DisplayName("重複刷新session")
    void addWithRefreshTokenTwice() {
        User user = Instancio.create(User.class);
        Session session = sessionService.add(user, "web", "");

        Session added1 = sessionService.refresh(user, session.getToken(), "web", "");
        Session added2 = sessionService.refresh(user, session.getToken(), "web", "");

        Assertions.assertNotEquals(added1.getToken(), session.getToken());
        Assertions.assertEquals(added1.getToken(), added2.getToken());
    }

    @Test
    @DisplayName("移除session")
    void remove() {
        User user = Instancio.create(User.class);
        sessionService.add(user, "web", "");

        sessionService.remove(user, "web");

        Session session = sessionService.getInfo(user.getId(), "");
        Assertions.assertNotNull(session);
        Assertions.assertFalse(session.getEnable());
    }

}
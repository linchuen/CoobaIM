package com.cooba.service.impl;

import com.cooba.annotation.MybatisLocalTest;
import com.cooba.constant.JwtSecret;
import com.cooba.entity.Session;
import com.cooba.entity.User;
import com.cooba.repository.SessionRepository;
import com.cooba.service.SessionService;
import com.cooba.util.JwtUtil;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

@Rollback(value = false)
@MybatisLocalTest
@ContextConfiguration(classes = {SessionServiceImpl.class, JwtUtil.class, JwtSecret.class})
@Sql(scripts = {"/sql/Session-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class SessionServiceImplTest {
    @Autowired
    SessionService sessionService;
    @Autowired
    SessionRepository sessionRepository;

    @Test
    void add() {
        User user = Instancio.create(User.class);
        sessionService.add(user, "", "");

        Session session = sessionService.getInfo(user.getId(), "");
        Assertions.assertNotNull(session);
        Assertions.assertTrue(session.getEnable());
    }

    @Test
    void remove() {
        User user = Instancio.create(User.class);
        sessionService.add(user, "", "");

        sessionService.remove(user, "");

        Session session = sessionService.getInfo(user.getId(), "");
        Assertions.assertNotNull(session);
        Assertions.assertFalse(session.getEnable());
    }

}
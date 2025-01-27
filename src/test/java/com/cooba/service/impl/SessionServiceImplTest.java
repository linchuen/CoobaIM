package com.cooba.service.impl;

import com.cooba.annotation.MybatisLocalTest;
import com.cooba.constant.JwtSecret;
import com.cooba.repository.SessionRepository;
import com.cooba.service.SessionService;
import com.cooba.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

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

    }

    @Test
    void remove() {
    }

    @Test
    void getInfo() {
    }
}
package com.cooba.service.impl;

import com.cooba.annotation.MybatisLocalTest;
import com.cooba.repository.SessionRepository;
import com.cooba.service.SessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@Rollback(value = false)
@MybatisLocalTest
@ContextConfiguration(classes = {SessionServiceImpl.class})
@Sql(scripts = {"/sql/Session-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class SessionServiceImplTest {
    @Autowired
    SessionService sessionService;
    @Autowired
    SessionRepository sessionRepository;

    @Test
    void add() {
        sessionService.add();
    }

    @Test
    void remove() {
    }

    @Test
    void getInfo() {
    }
}
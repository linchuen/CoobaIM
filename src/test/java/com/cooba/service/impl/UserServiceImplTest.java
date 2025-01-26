package com.cooba.service.impl;

import com.cooba.annotation.MybatisLocalTest;
import com.cooba.core.SocketConnection;
import com.cooba.entity.User;
import com.cooba.repository.UserRepository;
import com.cooba.service.UserService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;
@Rollback(value = false)
@MybatisLocalTest
@ContextConfiguration(classes = {UserServiceImpl.class})
@Sql(scripts = {"/sql/User-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class UserServiceImplTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @MockitoBean
    SocketConnection socketConnection;

    @Test
    void register() {
        User user = Instancio.create(User.class);
        userService.register(user);

        User select = userRepository.selectById(user.getId());
        Assertions.assertNotNull(select);
    }

    @Test
    void loadUserByUsername() {
        User user = Instancio.create(User.class);
        userService.register(user);

        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        Assertions.assertNotNull(userDetails);
    }
}
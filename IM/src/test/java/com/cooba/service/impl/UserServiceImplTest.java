package com.cooba.service.impl;

import com.cooba.annotation.MybatisLocalTest;
import com.cooba.aop.UserThreadLocal;
import com.cooba.core.SocketConnection;
import com.cooba.entity.User;
import com.cooba.entity.UserDetail;
import com.cooba.repository.UserDetailRepository;
import com.cooba.repository.UserRepository;
import com.cooba.service.UserService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@MybatisLocalTest
@ContextConfiguration(classes = {UserServiceImpl.class})
@Sql(scripts = {"/sql/User-schema.sql", "/sql/UserDetail-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = {"/delete/User-delete.sql", "/delete/UserDetail-delete.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class UserServiceImplTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserDetailRepository userDetailRepository;
    @MockitoBean
    SocketConnection socketConnection;
    @MockitoBean
    RedisTemplate redisTemplate;
    @MockitoBean
    UserThreadLocal userThreadLocal;

    @Test
    void register() {
        User user = Instancio.create(User.class);
        user.setEmail("cooba@gmail.com");
        user.setPassword("Qq123456");
        userService.register(user);

        User select = userRepository.selectById(user.getId());
        Assertions.assertNotNull(select);

        List<UserDetail> details = userDetailRepository.findByUserId(List.of(user.getId()));
        Assertions.assertEquals(1, details.size());
        Assertions.assertEquals("", details.get(0).getRemark());
    }
}
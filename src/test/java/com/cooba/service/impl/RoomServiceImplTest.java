package com.cooba.service.impl;

import com.cooba.annotation.MybatisLocalTest;
import com.cooba.aop.UserThreadLocal;
import com.cooba.entity.Room;
import com.cooba.entity.RoomUser;
import com.cooba.repository.RoomRepository;
import com.cooba.repository.RoomUserRepository;
import com.cooba.service.RoomService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@Rollback(value = false)
@MybatisLocalTest
@ContextConfiguration(classes = {RoomServiceImpl.class})
@Sql(scripts = {"/sql/Room-schema.sql", "/sql/RoomUser-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class RoomServiceImplTest {
    @Autowired
    RoomService roomService;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    RoomUserRepository roomUserRepository;
    @MockitoBean
    UserThreadLocal userThreadLocal;

    @Test
    @DisplayName("建立聊天室")
    void build() {
        long userId = 1L;
        Mockito.when(userThreadLocal.getCurrentUserId()).thenReturn(userId);

        Room room = Instancio.create(Room.class);
        roomService.build(room);

        Room select = roomRepository.selectById(room.getId());
        Assertions.assertNotNull(select);

        RoomUser roomUser = roomService.getRoomUserInfo(room.getId(), userId);
        Assertions.assertNotNull(roomUser);
    }

    @Test
    @DisplayName("刪除聊天室")
    void destroy() {
        Mockito.when(userThreadLocal.getCurrentUserId()).thenReturn(1L);

        Room room = Instancio.create(Room.class);
        roomService.build(room);
        roomService.destroy(room.getId());

        Room select = roomRepository.selectById(room.getId());
        Assertions.assertNull(select);

        List<RoomUser> roomUsers = roomService.getRoomUsers(room.getId());
        Assertions.assertTrue(roomUsers.isEmpty());
    }

    @Test
    void addUser() {
        Room room = Instancio.create(Room.class);
        roomService.build(room);

        RoomUser roomUser = Instancio.create(RoomUser.class);
        roomUser.setRoomId(room.getId());

        roomService.addUser(roomUser);

        RoomUser select = roomUserRepository.selectById(roomUser.getId());
        Assertions.assertNotNull(select);
    }

    @Test
    void deleteUser() {
        Room room = Instancio.create(Room.class);
        roomService.build(room);

        RoomUser roomUser = Instancio.create(RoomUser.class);
        roomUser.setRoomId(room.getId());

        roomService.addUser(roomUser);
        roomService.deleteUser(roomUser);

        RoomUser select = roomUserRepository.selectById(roomUser.getId());
        Assertions.assertNull(select);
    }
}
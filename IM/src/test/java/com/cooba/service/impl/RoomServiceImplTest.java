package com.cooba.service.impl;

import com.cooba.annotation.MybatisLocalTest;
import com.cooba.aop.UserThreadLocal;
import com.cooba.constant.RoomRoleEnum;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@MybatisLocalTest
@ContextConfiguration(classes = {RoomServiceImpl.class})
@Sql(scripts = {"/sql/Room-schema.sql", "/sql/RoomUser-schema.sql", "/sql/User-schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = {"/delete/Room-delete.sql", "/delete/RoomUser-delete.sql", "/delete/User-delete.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
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
        Mockito.when(userThreadLocal.getCurrentUserName()).thenReturn("test name");

        Room room = Instancio.create(Room.class);
        roomService.build(room);

        Room select = roomRepository.selectById(room.getId());
        Assertions.assertNotNull(select);

        RoomUser roomUser = roomService.getRoomUserInfo(room.getId(), userId);
        Assertions.assertNotNull(roomUser);
    }

    @Test
    @DisplayName("建立聊天室和添加用戶")
    void buildWithUser() {
        long masterId = 1L;
        long memberId = 2L;
        Mockito.when(userThreadLocal.getCurrentUserId()).thenReturn(masterId);
        Mockito.when(userThreadLocal.getCurrentUserName()).thenReturn("test name");

        Room room = Instancio.create(Room.class);
        roomService.build(room, List.of(memberId));

        Room select = roomRepository.selectById(room.getId());
        Assertions.assertNotNull(select);

        RoomUser roomMaster = roomService.getRoomUserInfo(room.getId(), masterId);
        Assertions.assertNotNull(roomMaster);
        Assertions.assertSame(roomMaster.getRoomRoleEnum(), RoomRoleEnum.MASTER);

        RoomUser roomMember = roomService.getRoomUserInfo(room.getId(), memberId);
        Assertions.assertNotNull(roomMember);
        Assertions.assertSame(roomMember.getRoomRoleEnum(), RoomRoleEnum.MEMBER);
    }

    @Test
    @DisplayName("刪除聊天室")
    void destroy() {
        Mockito.when(userThreadLocal.getCurrentUserId()).thenReturn(1L);
        Mockito.when(userThreadLocal.getCurrentUserName()).thenReturn("test name");

        Room room = Instancio.create(Room.class);
        roomService.build(room);
        roomService.destroy(room.getId());

        Room select = roomRepository.selectById(room.getId());
        Assertions.assertNull(select);

        List<RoomUser> roomUsers = roomService.getRoomUsers(room.getId());
        Assertions.assertTrue(roomUsers.isEmpty());
    }

    @Test
    @DisplayName("新增聊天室用戶")
    void addUser() {
        Mockito.when(userThreadLocal.getCurrentUserId()).thenReturn(1L);
        Mockito.when(userThreadLocal.getCurrentUserName()).thenReturn("test name");

        Room room = Instancio.create(Room.class);
        roomService.build(room);

        RoomUser roomUser = Instancio.create(RoomUser.class);
        roomUser.setRoomId(room.getId());

        roomService.addUser(roomUser);

        RoomUser select = roomUserRepository.selectById(roomUser.getId());
        Assertions.assertNotNull(select);
    }

    @Test
    @DisplayName("刪除聊天室用戶")
    void deleteUser() {
        Mockito.when(userThreadLocal.getCurrentUserId()).thenReturn(1L);
        Mockito.when(userThreadLocal.getCurrentUserName()).thenReturn("test name");

        Room room = Instancio.create(Room.class);
        roomService.build(room);

        RoomUser roomUser = Instancio.create(RoomUser.class);
        roomUser.setRoomId(room.getId());

        roomService.addUser(roomUser);
        roomService.deleteUser(roomUser);

        RoomUser select = roomUserRepository.selectById(roomUser.getId());
        Assertions.assertNull(select);
    }

    @Test
    @DisplayName("轉移用戶權限")
    void transferUser() {
        long masterId = 1L;
        long memberId = 2L;
        Mockito.when(userThreadLocal.getCurrentUserId()).thenReturn(masterId);
        Mockito.when(userThreadLocal.getCurrentUserName()).thenReturn("test name");

        Room room = Instancio.create(Room.class);
        roomService.build(room, List.of(memberId));

        roomService.transferUser(room.getId(), masterId, memberId);

        RoomUser member = roomUserRepository.find(room.getId(),memberId).orElseThrow();
        Assertions.assertSame(RoomRoleEnum.MASTER,member.getRoomRoleEnum() );
    }

}
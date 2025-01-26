package com.cooba.service.impl;

import com.cooba.annotation.MybatisLocalTest;
import com.cooba.entity.Room;
import com.cooba.entity.RoomUser;
import com.cooba.repository.RoomRepository;
import com.cooba.repository.RoomUserRepository;
import com.cooba.service.RoomService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void build() {
        Room room = Instancio.create(Room.class);
        roomService.build(room);

        Room select = roomRepository.selectById(room.getId());
        Assertions.assertNotNull(select);
    }

    @Test
    void destroy() {
        Room room = Instancio.create(Room.class);
        roomService.build(room);
        roomService.destroy(room);

        Room select = roomRepository.selectById(room.getId());
        Assertions.assertNull(select);
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
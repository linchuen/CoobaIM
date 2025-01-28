package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.entity.Room;
import com.cooba.entity.RoomUser;
import com.cooba.exception.BaseException;
import com.cooba.repository.RoomRepository;
import com.cooba.repository.RoomUserRepository;
import com.cooba.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomUserRepository roomUserRepository;

    @Override
    public long build(Room room) {
        roomRepository.insert(room);
        return room.getId();
    }

    @Override
    public void destroy(Room room) {
        roomRepository.deleteById(room);
    }

    @Override
    public void addUser(RoomUser roomUser) {
        Room room = roomRepository.selectById(roomUser.getRoomId());
        if (room == null) throw new BaseException();

        roomUserRepository.insert(roomUser);
    }

    @Override
    public void deleteUser(RoomUser roomUser) {
        Room room = roomRepository.selectById(roomUser.getRoomId());
        if (room == null) throw new BaseException();

        roomUserRepository.deleteById(roomUser);
    }
}

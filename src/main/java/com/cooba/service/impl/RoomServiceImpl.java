package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.entity.Room;
import com.cooba.entity.RoomUser;
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
    public void build(Room room) {
        roomRepository.insert(room);
    }

    @Override
    public void destroy(Room room) {
        roomRepository.updateById(room);
    }

    @Override
    public void addUser(RoomUser roomUser) {
        roomUserRepository.insert(roomUser);
    }

    @Override
    public void deleteUser(RoomUser roomUser) {
        roomUserRepository.deleteById(roomUser);
    }
}

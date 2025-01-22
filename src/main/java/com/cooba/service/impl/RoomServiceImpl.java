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
    public void build() {
        roomRepository.insert(new Room());
    }

    @Override
    public void destroy() {
        roomRepository.updateById(new Room());
    }

    @Override
    public void addUser() {
        roomUserRepository.insert(new RoomUser());
    }

    @Override
    public void deleteUser() {
        roomUserRepository.deleteById(new RoomUser());
    }
}

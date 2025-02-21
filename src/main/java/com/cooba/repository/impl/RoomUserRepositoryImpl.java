package com.cooba.repository.impl;

import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.Chat;
import com.cooba.entity.RoomUser;
import com.cooba.mapper.ChatMapper;
import com.cooba.mapper.RoomUserMapper;
import com.cooba.repository.ChatRepository;
import com.cooba.repository.RoomUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
public class RoomUserRepositoryImpl implements RoomUserRepository {
    private final RoomUserMapper roomUserMapper;

    @Override
    public void insert(RoomUser roomUser) {
        roomUserMapper.insert(roomUser);
    }

    @Override
    public void insert(List<RoomUser> roomUsers) {
        roomUserMapper.insert(roomUsers);

    }
}

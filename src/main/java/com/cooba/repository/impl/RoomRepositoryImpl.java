package com.cooba.repository.impl;

import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.Room;
import com.cooba.mapper.RoomMapper;
import com.cooba.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepository {
    private final RoomMapper roomMapper;

    @Override
    public void insert(Room room) {
        roomMapper.insert(room);
    }
}


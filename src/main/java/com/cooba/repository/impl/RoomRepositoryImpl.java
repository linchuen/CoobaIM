package com.cooba.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.Room;
import com.cooba.entity.RoomUser;
import com.cooba.mapper.RoomMapper;
import com.cooba.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepository {
    private final RoomMapper roomMapper;

    @Override
    public void insert(Room room) {
        roomMapper.insert(room);
    }

    @Override
    public void insert(List<Room> rooms) {
        roomMapper.insert(rooms);
    }

    @Override
    public Room selectById(long id) {
        return roomMapper.selectById(id);
    }

    @Override
    public void deleteById(long id) {
        roomMapper.deleteById(id);
    }

    @Override
    public List<Room> selectByIds(List<Long> ids) {
        if (ids.isEmpty()){
            return roomMapper.selectList(new LambdaQueryWrapper<>());
        }
        return roomMapper.selectByIds(ids);
    }
}


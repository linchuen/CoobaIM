package com.cooba.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.RoomUser;
import com.cooba.mapper.RoomUserMapper;
import com.cooba.repository.RoomUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;


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

    @Override
    public RoomUser selectById(long id) {
        return roomUserMapper.selectById(id);
    }

    @Override
    public List<RoomUser> selectByIds(List<Long> ids) {
        return roomUserMapper.selectByIds(ids);
    }

    @Override
    public void deleteById(long id) {
        roomUserMapper.deleteById(id);
    }

    @Override
    public void delete(long roomId) {
        roomUserMapper.delete(new LambdaQueryWrapper<RoomUser>()
                .eq(RoomUser::getRoomId, roomId));
    }

    @Override
    public void delete(long roomId, long userId) {
        roomUserMapper.delete(new LambdaQueryWrapper<RoomUser>()
                .eq(RoomUser::getRoomId, roomId));
    }

    @Override
    public void delete(long roomId, List<Long> userIds) {
        roomUserMapper.delete(new LambdaQueryWrapper<RoomUser>()
                .eq(RoomUser::getRoomId, roomId)
                .in(RoomUser::getUserId, userIds));
    }

    @Override
    public List<RoomUser> find(long roomId) {
        return roomUserMapper.selectList(new LambdaQueryWrapper<RoomUser>()
                .eq(RoomUser::getRoomId, roomId));
    }

    @Override
    public List<RoomUser> findByUserId(long userId) {
        return roomUserMapper.selectList(new LambdaQueryWrapper<RoomUser>()
                .eq(RoomUser::getUserId, userId));
    }

    @Override
    public Optional<RoomUser> find(long roomId, long userId) {
        RoomUser roomUser = roomUserMapper.selectOne(new LambdaQueryWrapper<RoomUser>()
                .eq(RoomUser::getRoomId, roomId)
                .eq(RoomUser::getUserId, userId));
        return Optional.ofNullable(roomUser);
    }
}

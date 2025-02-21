package com.cooba.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.Friend;
import com.cooba.mapper.FriendMapper;
import com.cooba.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;


@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
public class FriendRepositoryImpl implements FriendRepository {
    private final FriendMapper friendMapper;

    @Override
    public void insert(Friend friend) {
        friendMapper.insert(friend);
    }

    @Override
    public void insert(List<Friend> friends) {
        friendMapper.insert(friends);
    }

    @Override
    public Friend selectById(long id) {
        return null;
    }

    @Override
    public void delete(long userId, long friendId) {
        friendMapper.delete(new LambdaQueryWrapper<Friend>()
                .eq(Friend::getUserId, userId)
                .eq(Friend::getFriendUserId, friendId));
    }

    @Override
    public void addRoomId(List<Long> userIds, long roomId) {
        Friend friend = new Friend();
        friend.setRoomId(roomId);
        friendMapper.update(friend, new LambdaQueryWrapper<Friend>()
                .in(Friend::getUserId, userIds));
    }

    @Override
    public List<Friend> find(long userId) {
        return friendMapper.selectList(new LambdaQueryWrapper<Friend>()
                .eq(Friend::getUserId, userId));
    }

    @Override
    public Optional<Friend> find(long userId, long friendId) {
        Friend friend = friendMapper.selectOne(new LambdaQueryWrapper<Friend>()
                .eq(Friend::getUserId, userId)
                .eq(Friend::getFriendUserId, friendId));
        return Optional.ofNullable(friend);
    }
}

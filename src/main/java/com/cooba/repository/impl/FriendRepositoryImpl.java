package com.cooba.repository.impl;

import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.Chat;
import com.cooba.entity.Friend;
import com.cooba.mapper.ChatMapper;
import com.cooba.mapper.FriendMapper;
import com.cooba.repository.ChatRepository;
import com.cooba.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


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
}

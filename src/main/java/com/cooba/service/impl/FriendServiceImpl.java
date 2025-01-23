package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.entity.Friend;
import com.cooba.repository.FriendRepository;
import com.cooba.service.FriendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;
    @Override
    public void apply(Friend friend) {
        friendRepository.insert(friend);
    }

    @Override
    public void bind(Friend friend) {
        friendRepository.updateById(friend);
    }

    @Override
    public void unbind(Friend friend) {
        friendRepository.deleteById(friend);
    }
}

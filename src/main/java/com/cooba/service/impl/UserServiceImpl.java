package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.entity.RoomUser;
import com.cooba.entity.User;
import com.cooba.exception.BaseException;
import com.cooba.repository.RoomUserRepository;
import com.cooba.repository.UserRepository;
import com.cooba.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoomUserRepository roomUserRepository;

    @Override
    public void register(User user) {
        userRepository.insert(user);
    }

    @Override
    public User enterRoom(RoomUser roomUser) {
        User user = getInfo(roomUser.getUserId());

        roomUserRepository.insert(roomUser);
        return user;
    }

    @Override
    public User leaveRoom(RoomUser roomUser) {
        User user = getInfo(roomUser.getUserId());

        roomUserRepository.deleteById(roomUser);
        return user;
    }

    @Override
    public User getInfo(long userId) {
        User user = userRepository.selectById(userId);
        if (user == null) throw new BaseException();

        return user;
    }
}

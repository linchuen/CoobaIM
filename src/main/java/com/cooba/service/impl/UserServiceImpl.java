package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.entity.RoomUser;
import com.cooba.entity.User;
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
    public void register() {
        userRepository.insert(new User());
    }

    @Override
    public void login() {

    }

    @Override
    public void logout() {

    }

    @Override
    public void enterRoom() {
        roomUserRepository.insert(new RoomUser());
    }

    @Override
    public void leaveRoom() {
        roomUserRepository.deleteById(new RoomUser());
    }
}

package com.cooba.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.BehaviorLayer;
import com.cooba.core.SocketConnection;
import com.cooba.entity.RoomUser;
import com.cooba.entity.User;
import com.cooba.exception.BaseException;
import com.cooba.repository.RoomUserRepository;
import com.cooba.repository.UserRepository;
import com.cooba.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoomUserRepository roomUserRepository;
    private final SocketConnection socketConnection;

    @Override
    public void register(User user) {
        userRepository.insert(user);
    }

    @Override
    public void connectRoom(RoomUser roomUser) {
        socketConnection.bindGroup(String.valueOf(roomUser.getUserId()), String.valueOf(roomUser.getRoomId()));
    }

    @Override
    public void disconnectRoom(RoomUser roomUser) {
        roomUserRepository.deleteById(roomUser);
    }

    @Override
    public User getInfo(long userId) {
        User user = userRepository.selectById(userId);
        if (user == null) throw new BaseException();

        return user;
    }

    @Override
    public List<RoomUser> getAllRooms(long userId) {
        return roomUserRepository.selectList(new LambdaQueryWrapper<RoomUser>()
                .eq(RoomUser::getUserId, userId));
    }
}

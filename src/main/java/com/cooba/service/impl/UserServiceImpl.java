package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.constant.ErrorEnum;
import com.cooba.core.SocketConnection;
import com.cooba.entity.RoomUser;
import com.cooba.entity.User;
import com.cooba.exception.BaseException;
import com.cooba.repository.RoomUserRepository;
import com.cooba.repository.UserRepository;
import com.cooba.service.UserService;
import com.cooba.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoomUserRepository roomUserRepository;
    private final SocketConnection socketConnection;

    @Override
    public long register(User user) {
        String password = user.getPassword();
        user.setPassword(PasswordUtil.hash(password));
        userRepository.insert(user);

        return user.getId();
    }

    @Override
    public void connectRoom(RoomUser roomUser) {
        socketConnection.bindGroup(String.valueOf(roomUser.getUserId()), String.valueOf(roomUser.getRoomId()));
    }

    @Override
    public void disconnectRoom(RoomUser roomUser) {
        socketConnection.unbindGroup(String.valueOf(roomUser.getUserId()), String.valueOf(roomUser.getRoomId()));
    }

    @Override
    public void verifyPassword(User user, String password) {
        String hashPassword = PasswordUtil.hash(password);
        if (!user.getPassword().equals(hashPassword)) {
            throw new BaseException();
        }
    }

    @Override
    public User getInfo(long userId) {
        User user = userRepository.selectById(userId);
        if (user == null) throw new BaseException();

        return user;
    }

    @Override
    public User getInfo(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(ErrorEnum.USER_NOT_EXIST));
    }

    @Override
    public List<RoomUser> getAllRooms(long userId) {
        return roomUserRepository.findByUserId(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorEnum.USER_NOT_EXIST.getMessage()));
    }
}

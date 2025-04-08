package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.constant.ErrorEnum;
import com.cooba.core.SocketConnection;
import com.cooba.entity.RoomUser;
import com.cooba.entity.User;
import com.cooba.entity.UserDetail;
import com.cooba.exception.BaseException;
import com.cooba.repository.RoomUserRepository;
import com.cooba.repository.UserDetailRepository;
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
    private final UserDetailRepository userDetailRepository;
    private final RoomUserRepository roomUserRepository;
    private final SocketConnection socketConnection;

    @Override
    public long register(User user) {
        String password = user.getPassword();
        user.setPassword(PasswordUtil.hash(password));
        userRepository.insert(user);

        UserDetail userDetail = new UserDetail();
        userDetail.setUserId(user.getId());
        userDetail.setName(user.getName());
        userDetail.setEmail(user.getEmail());
        userDetail.setTags("[]");
        userDetail.setRemark("");
        userDetailRepository.insert(userDetail);
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
        if (user == null) throw new BaseException(ErrorEnum.USER_NOT_EXIST);

        return user;
    }

    @Override
    public User getInfo(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(ErrorEnum.USER_NOT_EXIST));
    }

    @Override
    public User getInfoByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new BaseException(ErrorEnum.USER_NOT_EXIST));
    }

    @Override
    public UserDetail getDetail(long userId) {
        return userDetailRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(ErrorEnum.USER_NOT_EXIST));
    }

    @Override
    public List<User> getInfoList(List<Long> userIds) {
        return userRepository.selectByIds(userIds);
    }

    @Override
    public List<RoomUser> getAllRooms(long userId) {
        return roomUserRepository.findByUserId(userId);
    }

    @Override
    public List<UserDetail> getDetailList(List<Long> userIds) {
        return userDetailRepository.findByUserId(userIds);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorEnum.USER_NOT_EXIST.getMessage()));
    }
}

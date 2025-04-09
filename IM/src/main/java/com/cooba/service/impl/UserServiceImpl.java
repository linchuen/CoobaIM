package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.aop.UserThreadLocal;
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
    private final UserThreadLocal userThreadLocal;

    @Override
    public long register(User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new BaseException(ErrorEnum.INVALID_EMAIL_FORMAT);
        }

        // 密碼格式驗證：至少一個大寫、一個小寫、一個數字，長度至少6位
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$")) {
            throw new BaseException(ErrorEnum.INVALID_PASSWORD_FORMAT);
        }

        user.setPassword(PasswordUtil.hash(password));
        userRepository.insert(user);

        UserDetail userDetail = new UserDetail();
        userDetail.setUserId(user.getId());
        userDetail.setName(user.getName());
        userDetail.setEmail(email);
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
    public User getInfo(String email, String partner) {
        return userRepository.findByEmail(email, partner)
                .orElseThrow(() -> new BaseException(ErrorEnum.USER_NOT_EXIST));
    }

    @Override
    public User getInfoByName(String name, String partner) {
        return userRepository.findByName(name, partner)
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
        return userRepository.findByName(username, userThreadLocal.getPartner())
                .orElseThrow(() -> new UsernameNotFoundException(ErrorEnum.USER_NOT_EXIST.getMessage()));
    }
}

package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.aop.UserThreadLocal;
import com.cooba.component.UserComponent;
import com.cooba.constant.ErrorEnum;
import com.cooba.dto.NotifyMessage;
import com.cooba.dto.SendMessage;
import com.cooba.dto.request.*;
import com.cooba.dto.response.*;
import com.cooba.entity.*;
import com.cooba.exception.BaseException;
import com.cooba.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@ObjectLayer
@RequiredArgsConstructor
public class UserComponentImpl implements UserComponent {
    private final UserService userService;
    private final RoomService roomService;
    private final MessageService messageService;
    private final FriendService friendService;
    private final SessionService sessionService;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        long userId = userService.register(user);
        return RegisterResponse.builder()
                .userId(userId)
                .build();
    }

    @Override
    public User getInfo(long userId) {
        return userService.getInfo(userId);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userService.getInfo(request.getUserId());

        userService.verifyPassword(user, request.getPassword());

        Session session = sessionService.add(user);

        userService.getAllRooms(user.getId()).forEach(userService::connectRoom);

        return LoginResponse.builder()
                .userId(session.getUserId())
                .platform(session.getPlatform())
                .token(session.getToken())
                .ip(session.getIp())
                .loginTime(session.getLoginTime())
                .expireTime(session.getExpireTime())
                .build();
    }

    @Override
    public LogoutResponse logout(LogoutRequest request) {
        User user = userService.getInfo(request.getUserId());

        LocalDateTime logoutTime = sessionService.remove(user);

        userService.getAllRooms(user.getId()).forEach(userService::disconnectRoom);

        return LogoutResponse.builder()
                .logoutTime(logoutTime)
                .build();
    }

    @Override
    public boolean isOnline(long userId) {
        return sessionService.getInfo(userId).getEnable();
    }

    @Override
    public RoomResponse enterRoom(RoomUserRequest request) {
        RoomUser roomUser = new RoomUser();
        roomUser.setRoomId(request.getRoomId());
        roomUser.setUserId(request.getUserId());

        roomService.addUser(roomUser);

        userService.connectRoom(roomUser);

        return RoomResponse.builder()
                .roomId(request.getRoomId())
                .userId(request.getUserId())
                .build();
    }

    @Override
    public RoomResponse leaveRoom(RoomUserRequest request) {
        RoomUser roomUser = new RoomUser();
        roomUser.setRoomId(request.getRoomId());
        roomUser.setUserId(request.getUserId());

        roomService.deleteUser(roomUser);

        userService.disconnectRoom(roomUser);

        return RoomResponse.builder()
                .roomId(request.getRoomId())
                .userId(request.getUserId())
                .build();
    }

    @Override
    public void speakToUser(SpeakRequest request) {
        SendMessage message = new SendMessage();
        BeanUtils.copyProperties(request, message);

        messageService.sendToUser(message);
    }

    @Override
    public void speakToRoom(SpeakRequest request) {
        SendMessage message = new SendMessage();
        BeanUtils.copyProperties(request, message);

        messageService.sendToRoom(message);
    }

    @Override
    public void speakToAll(SpeakRequest request) {
        NotifyMessage message = new NotifyMessage();
        BeanUtils.copyProperties(request, message);

        messageService.sendToAll(message);
    }

    @Override
    public ApplyFriendResponse applyFriend(FriendRequest request) {
        FriendApply friendApply = new FriendApply();
        friendApply.setApplyUserId(request.getApplyUserId());
        friendApply.setPermitUserId(request.getPermitUserId());

        long applyId = friendService.apply(friendApply);

        return ApplyFriendResponse.builder()
                .applyId(applyId)
                .build();
    }

    @Override
    public void permitFriendApply(FriendRequest request) {
        Long userId = UserThreadLocal.get().getId();
        if (!Objects.equals(userId, request.getPermitUserId())) throw new BaseException(ErrorEnum.FORBIDDEN);

        FriendApply friendApply = new FriendApply();
        friendApply.setApplyUserId(request.getApplyUserId());
        friendApply.setPermitUserId(request.getPermitUserId());
        friendApply.setPermit(request.getIsPermit());

        friendService.bind(friendApply);
    }

    @Override
    public void removeFriend(FriendRequest request) {
        FriendApply friendApply = new FriendApply();
        friendApply.setApplyUserId(request.getApplyUserId());
        friendApply.setPermitUserId(request.getPermitUserId());

        friendService.unbind(friendApply);
    }
}

package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.component.UserComponent;
import com.cooba.dto.NotifyMessage;
import com.cooba.dto.SendMessage;
import com.cooba.dto.request.*;
import com.cooba.dto.response.RegisterResponse;
import com.cooba.entity.*;
import com.cooba.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

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
    public void login(SessionRequest request) {
        User user = userService.getInfo(request.getUserId());

        userService.verifyPassword(user, user.getPassword());

        sessionService.add(user);

        userService.getAllRooms(user.getId()).forEach(userService::connectRoom);
    }

    @Override
    public void logout(SessionRequest request) {
        User user = userService.getInfo(request.getUserId());

        sessionService.remove(user);

        userService.getAllRooms(user.getId()).forEach(userService::disconnectRoom);
    }

    @Override
    public boolean isOnline(long userId) {
        return sessionService.getInfo(userId) != null;
    }

    @Override
    public void enterRoom(RoomUserRequest request) {
        RoomUser roomUser = new RoomUser();
        BeanUtils.copyProperties(request, roomUser);

        roomService.addUser(roomUser);
    }

    @Override
    public void leaveRoom(RoomUserRequest request) {
        RoomUser roomUser = new RoomUser();
        BeanUtils.copyProperties(request, roomUser);

        roomService.deleteUser(roomUser);
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
    public void addFriend(FriendRequest request) {
        FriendApply friendApply = new FriendApply();
        BeanUtils.copyProperties(request, friendApply);

        friendService.apply(friendApply);
    }

    @Override
    public void permitFriendApply(FriendRequest request) {
        FriendApply friendApply = new FriendApply();
        BeanUtils.copyProperties(request, friendApply);

        friendService.bind(friendApply);
    }

    @Override
    public void removeFriend(FriendRequest request) {
        FriendApply friendApply = new FriendApply();
        BeanUtils.copyProperties(request, friendApply);

        friendService.unbind(friendApply);
    }
}

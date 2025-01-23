package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.component.UserComponent;
import com.cooba.dto.NotifyMessage;
import com.cooba.dto.SendMessage;
import com.cooba.dto.request.FriendRequest;
import com.cooba.dto.request.RegisterRequest;
import com.cooba.dto.request.RoomUserRequest;
import com.cooba.dto.request.SpeakRequest;
import com.cooba.entity.Friend;
import com.cooba.entity.RoomUser;
import com.cooba.entity.User;
import com.cooba.service.FriendService;
import com.cooba.service.MessageService;
import com.cooba.service.SessionService;
import com.cooba.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

@Slf4j
@ObjectLayer
@RequiredArgsConstructor
public class UserComponentImpl implements UserComponent {
    private final UserService userService;
    private final MessageService messageService;
    private final FriendService friendService;
    private final SessionService sessionService;

    @Override
    public void register(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());

        userService.register(user);
    }

    @Override
    public void login() {
        sessionService.add();
    }

    @Override
    public void logout() {
        sessionService.remove();
    }

    @Override
    public void enterRoom(RoomUserRequest request) {
        RoomUser roomUser = new RoomUser();
        BeanUtils.copyProperties(request, roomUser);
        User user = userService.enterRoom(roomUser);

        SendMessage message = new SendMessage();
        message.setRoomId(request.getRoomId());
        message.setUser(user);
        message.setMessage("進入聊天室");
        messageService.sendToRoom(message);
    }

    @Override
    public void leaveRoom(RoomUserRequest request) {
        RoomUser roomUser = new RoomUser();
        BeanUtils.copyProperties(request, roomUser);
        User user = userService.leaveRoom(roomUser);

        SendMessage message = new SendMessage();
        message.setRoomId(request.getRoomId());
        message.setUser(user);
        message.setMessage("離開聊天室");
        messageService.sendToRoom(message);
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
        Friend friend = new Friend();
        BeanUtils.copyProperties(request, friend);

        friendService.apply(friend);
    }

    @Override
    public void permitFriendApply(FriendRequest request) {
        Friend friend = new Friend();
        BeanUtils.copyProperties(request, friend);

        friendService.bind(friend);
    }

    @Override
    public void removeFriend(FriendRequest request) {
        Friend friend = new Friend();
        BeanUtils.copyProperties(request, friend);

        friendService.unbind(friend);
    }
}

package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.component.UserComponent;
import com.cooba.dto.NotifyMessage;
import com.cooba.dto.SendMessage;
import com.cooba.dto.request.RegisterRequest;
import com.cooba.entity.User;
import com.cooba.service.FriendService;
import com.cooba.service.MessageService;
import com.cooba.service.SessionService;
import com.cooba.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    public void enterRoom() {
        userService.enterRoom();
    }

    @Override
    public void leaveRoom() {
        userService.leaveRoom();
    }

    @Override
    public void speakToUser() {
        messageService.sendToUser(new SendMessage());
    }

    @Override
    public void speakToRoom() {
        messageService.sendToRoom(new SendMessage());
    }

    @Override
    public void speakToAll() {
        messageService.sendToAll(new NotifyMessage());
    }

    @Override
    public void addFriend() {
        friendService.apply();
    }

    @Override
    public void permitFriendApply() {
        friendService.bind();
    }

    @Override
    public void removeFriend() {
        friendService.unbind();
    }
}

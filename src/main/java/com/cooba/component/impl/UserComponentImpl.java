package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.component.UserComponent;
import com.cooba.service.FriendService;
import com.cooba.service.MessageService;
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

    @Override
    public void register() {
        userService.register();
    }

    @Override
    public void login() {
        userService.login();
    }

    @Override
    public void logout() {
        userService.logout();
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
        messageService.sendToUser();
    }

    @Override
    public void speakToRoom() {
        messageService.sendToRoom();
    }

    @Override
    public void speakToAll() {
        messageService.sendToAll();
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

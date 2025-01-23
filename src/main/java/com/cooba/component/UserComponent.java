package com.cooba.component;

import com.cooba.dto.request.RegisterRequest;

public interface UserComponent {

    void register(RegisterRequest request);

    void login();

    void logout();

    void enterRoom();

    void leaveRoom();

    void speakToUser();

    void speakToRoom();

    void speakToAll();

    void addFriend();

    void permitFriendApply();

    void removeFriend();
}

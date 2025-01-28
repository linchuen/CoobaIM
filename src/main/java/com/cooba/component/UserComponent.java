package com.cooba.component;

import com.cooba.dto.request.*;
import com.cooba.dto.response.RegisterResponse;
import com.cooba.entity.User;

public interface UserComponent {

    RegisterResponse register(RegisterRequest request);

    User getInfo(long userId);

    void login(SessionRequest request);

    void logout(SessionRequest request);

    boolean isOnline(long userId);

    void enterRoom(RoomUserRequest request);

    void leaveRoom(RoomUserRequest request);

    void speakToUser(SpeakRequest request);

    void speakToRoom(SpeakRequest request);

    void speakToAll(SpeakRequest request);

    void addFriend(FriendRequest request);

    void permitFriendApply(FriendRequest request);

    void removeFriend(FriendRequest request);
}

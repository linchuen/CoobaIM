package com.cooba.component;

import com.cooba.dto.request.*;
import com.cooba.dto.response.LoginResponse;
import com.cooba.dto.response.LogoutResponse;
import com.cooba.dto.response.RegisterResponse;
import com.cooba.dto.response.RoomResponse;
import com.cooba.entity.User;

public interface UserComponent {

    RegisterResponse register(RegisterRequest request);

    User getInfo(long userId);

    LoginResponse login(LoginRequest request);

    LogoutResponse logout(LogoutRequest request);

    boolean isOnline(long userId);

    RoomResponse enterRoom(RoomUserRequest request);

    RoomResponse leaveRoom(RoomUserRequest request);

    void speakToUser(SpeakRequest request);

    void speakToRoom(SpeakRequest request);

    void speakToAll(SpeakRequest request);

    void addFriend(FriendRequest request);

    void permitFriendApply(FriendRequest request);

    void removeFriend(FriendRequest request);
}

package com.cooba.component;

import com.cooba.dto.request.*;
import com.cooba.dto.response.*;
import com.cooba.entity.Friend;
import com.cooba.entity.User;

import java.util.List;

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

    ApplyFriendResponse applyFriend(FriendRequest request);

    void permitFriendApply(FriendRequest request);

    void removeFriend(FriendRemoveRequest request);

    List<Friend> searchFriend(FriendSearchRequest request);
}

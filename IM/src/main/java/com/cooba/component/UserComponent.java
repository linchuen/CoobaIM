package com.cooba.component;

import com.cooba.dto.request.*;
import com.cooba.dto.response.*;
import com.cooba.entity.User;

public interface UserComponent {

    RegisterResponse register(RegisterRequest request);

    User getInfo(long userId);

    LoginResponse login(LoginRequest request);

    LogoutResponse logout(LogoutRequest request);

    boolean isOnline(long userId);

    RoomResponse enterRoom(RoomUserRequest request);

    RoomResponse leaveRoom(RoomUserRequest request);

    FriendApplyResponse applyFriend(FriendRequest request);

    FriendPermitResponse permitFriendApply(FriendRequest request);

    void removeFriend(FriendRemoveRequest request);

    FriendSearchResponse searchFriend(FriendSearchRequest request);

    FriendSearchApplyResponse searchFriendApply();
}

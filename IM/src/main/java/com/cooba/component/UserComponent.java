package com.cooba.component;

import com.cooba.dto.request.*;
import com.cooba.dto.response.*;

public interface UserComponent {

    RegisterResponse register(RegisterRequest request);

    UserDetailResponse getDetail();

    LoginResponse login(LoginRequest request);

    LogoutResponse logout(LogoutRequest request);

    LoginResponse refreshToken(RefreshRequest request);

    RoomResponse enterRoom(RoomUserRequest request);

    RoomResponse leaveRoom(RoomUserRequest request);

    FriendApplyResponse applyFriend(FriendRequest request);

    FriendPermitResponse permitFriendApply(FriendRequest request);

    void removeFriend(FriendRemoveRequest request);

    FriendSearchResponse searchFriend(FriendSearchRequest request);

    FriendSearchApplyResponse searchFriendApply();
}

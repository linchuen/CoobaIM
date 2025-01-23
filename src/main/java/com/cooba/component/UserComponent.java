package com.cooba.component;

import com.cooba.dto.request.FriendRequest;
import com.cooba.dto.request.RegisterRequest;
import com.cooba.dto.request.RoomUserRequest;
import com.cooba.dto.request.SpeakRequest;

public interface UserComponent {

    void register(RegisterRequest request);

    void login();

    void logout();

    void enterRoom(RoomUserRequest request);

    void leaveRoom(RoomUserRequest request);

    void speakToUser(SpeakRequest request);

    void speakToRoom(SpeakRequest request);

    void speakToAll(SpeakRequest request);

    void addFriend(FriendRequest request);

    void permitFriendApply(FriendRequest request);

    void removeFriend(FriendRequest request);
}

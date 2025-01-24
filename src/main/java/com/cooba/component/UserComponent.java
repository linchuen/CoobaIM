package com.cooba.component;

import com.cooba.dto.request.*;

public interface UserComponent {

    void register(RegisterRequest request);

    void login(SessionRequest request);

    void logout(SessionRequest request);

    void enterRoom(RoomUserRequest request);

    void leaveRoom(RoomUserRequest request);

    void speakToUser(SpeakRequest request);

    void speakToRoom(SpeakRequest request);

    void speakToAll(SpeakRequest request);

    void addFriend(FriendRequest request);

    void permitFriendApply(FriendRequest request);

    void removeFriend(FriendRequest request);
}

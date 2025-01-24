package com.cooba.service;

import com.cooba.entity.RoomUser;
import com.cooba.entity.User;

public interface UserService {
    void register(User user);

    User enterRoom(RoomUser roomUser);

    User leaveRoom(RoomUser roomUser);

    User getInfo(long userId);
}

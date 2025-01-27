package com.cooba.service;

import com.cooba.entity.RoomUser;
import com.cooba.entity.User;

import java.util.List;

public interface UserService {
    void register(User user);

    void connectRoom(RoomUser roomUser);

    void disconnectRoom(RoomUser roomUser);

    void verifyPassword(User user, String password);

    User getInfo(long userId);

    List<RoomUser> getAllRooms(long userId);
}

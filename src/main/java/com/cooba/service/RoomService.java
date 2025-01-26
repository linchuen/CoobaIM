package com.cooba.service;

import com.cooba.entity.Room;
import com.cooba.entity.RoomUser;

public interface RoomService {
    void build(Room room);

    void destroy(Room room);

    void addUser(RoomUser roomUser);

    void deleteUser(RoomUser roomUser);
}

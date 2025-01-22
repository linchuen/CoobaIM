package com.cooba.service;

import com.cooba.entity.Room;
import com.cooba.entity.RoomUser;
import com.cooba.entity.User;

public interface RoomService {
    void build(Room room);

    void destroy(Room room);

    User addUser(RoomUser roomUser);

    User deleteUser(RoomUser roomUser);
}

package com.cooba.service;

import com.cooba.entity.Room;
import com.cooba.entity.RoomUser;

import java.util.List;

public interface RoomService {
    long build(Room room);

    void destroy(long roomId);

    void addUser(RoomUser roomUser);

    void deleteUser(RoomUser roomUser);

    void deleteUsers(long roomId, List<Long> userIds);

    RoomUser getRoomUserInfo(long roomId, long userId);

    boolean isRoomMember(long roomId, long userId);
}

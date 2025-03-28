package com.cooba.service;

import com.cooba.entity.Room;
import com.cooba.entity.RoomUser;

import java.util.List;

public interface RoomService {
    long build(Room room);

    long build(Room room, List<Long> userIds);

    long build(Room room, long masterUserId, List<Long> userIds);

    void destroy(long roomId);

    void addUser(RoomUser roomUser);

    void deleteUser(RoomUser roomUser);

    void transferUser(RoomUser masterUser, RoomUser transferUser);

    void deleteUsers(long roomId, List<Long> userIds);

    List<Room> searchRooms(long userId);


    List<RoomUser> getRoomUsers(long roomId);

    RoomUser getRoomUserInfo(long roomId, long userId);

    boolean isRoomMember(long roomId, long userId);
}

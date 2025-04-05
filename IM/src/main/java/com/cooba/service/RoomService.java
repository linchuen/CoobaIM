package com.cooba.service;

import com.cooba.constant.RoomRoleEnum;
import com.cooba.entity.Room;
import com.cooba.entity.RoomUser;
import com.cooba.entity.User;

import java.util.List;

public interface RoomService {
    long build(Room room);

    long build(Room room, List<Long> userIds);

    long build(Room room, long masterUserId, List<Long> userIds);

    void destroy(long roomId);

    void addUser(long roomId, User user, RoomRoleEnum roleEnum);

    void addUser(RoomUser roomUser);

    void deleteUser(RoomUser roomUser);

    void transferUser(long roomId, long masterUserId, long transferUserId);

    void deleteUsers(long roomId, List<Long> userIds);

    List<Room> searchRooms(long userId);

    List<RoomUser> getRoomUsers(long roomId);

    RoomUser getRoomUserInfo(long roomId, long userId);

    boolean isRoomMember(long roomId, long userId);
}

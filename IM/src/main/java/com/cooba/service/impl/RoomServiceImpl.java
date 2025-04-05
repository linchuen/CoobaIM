package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.aop.UserThreadLocal;
import com.cooba.constant.ErrorEnum;
import com.cooba.constant.RoomRoleEnum;
import com.cooba.entity.Room;
import com.cooba.entity.RoomUser;
import com.cooba.entity.User;
import com.cooba.exception.BaseException;
import com.cooba.repository.RoomRepository;
import com.cooba.repository.RoomUserRepository;
import com.cooba.repository.UserRepository;
import com.cooba.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomUserRepository roomUserRepository;
    private final UserRepository userRepository;
    private final UserThreadLocal userThreadLocal;

    @Override
    public long build(Room room) {
        return build(room, Collections.emptyList());
    }

    @Override
    public long build(Room room, List<Long> userIds) {
        return build(room, userThreadLocal.getCurrentUserId(), userIds);
    }

    @Override
    public long build(Room room, long masterUserId, List<Long> userIds) {
        roomRepository.insert(room);

        ArrayList<Long> userIdList = new ArrayList<>(userIds);
        userIdList.add(masterUserId);
        List<User> users = userRepository.selectByIds(userIdList);
        Map<Long, String> userMap = users.stream().collect(Collectors.toMap(User::getId, User::getName));
        Map<Long, String> avatarrMap = users.stream().collect(Collectors.toMap(User::getId, User::getAvatar));

        RoomUser roomMaster = new RoomUser();
        roomMaster.setUserId(masterUserId);
        roomMaster.setRoomId(room.getId());
        roomMaster.setShowName(userMap.getOrDefault(masterUserId, String.valueOf(masterUserId)));
        roomMaster.setRoomRoleEnum(RoomRoleEnum.MASTER);
        roomMaster.setAvatar(avatarrMap.get(masterUserId));

        List<RoomUser> roomUsers = userIds.stream().map(userId -> {
            RoomUser roomUser = new RoomUser();
            roomUser.setUserId(userId);
            roomUser.setRoomId(room.getId());
            roomUser.setShowName(userMap.getOrDefault(userId, String.valueOf(userId)));
            roomUser.setRoomRoleEnum(RoomRoleEnum.MEMBER);
            roomUser.setAvatar(avatarrMap.get(userId));
            return roomUser;
        }).collect(Collectors.toList());

        roomUsers.add(roomMaster);
        roomUserRepository.insert(roomUsers);
        return room.getId();
    }

    @Override
    public void destroy(long roomId) {
        roomRepository.deleteById(roomId);

        roomUserRepository.delete(roomId);
    }

    @Override
    public void addUser(long roomId, User user, RoomRoleEnum roleEnum) {
        RoomUser roomUser = new RoomUser();
        roomUser.setRoomId(roomId);
        roomUser.setUserId(user.getId());
        roomUser.setShowName(user.getName());
        roomUser.setAvatar(user.getAvatar());
        roomUser.setRoomRoleEnum(roleEnum);
        this.addUser(roomUser);
    }

    @Override
    public void addUser(RoomUser roomUser) {
        Room room = roomRepository.selectById(roomUser.getRoomId());
        if (room == null) throw new BaseException(ErrorEnum.ROOM_NOT_EXIST);

        roomUserRepository.insert(roomUser);
    }

    @Override
    public void deleteUser(RoomUser roomUser) {
        Room room = roomRepository.selectById(roomUser.getRoomId());
        if (room == null) throw new BaseException(ErrorEnum.ROOM_NOT_EXIST);

        roomUserRepository.delete(roomUser.getRoomId(), roomUser.getUserId());
    }

    @Override
    public void transferUser(long roomId, long masterUserId, long transferUserId) {
        roomUserRepository.update(roomId, masterUserId, RoomRoleEnum.MEMBER);
        roomUserRepository.update(roomId, transferUserId, RoomRoleEnum.MASTER);
    }

    @Override
    public void deleteUsers(long roomId, List<Long> userIds) {
        roomUserRepository.delete(roomId, userIds);
    }

    @Override
    public List<Room> searchRooms(long userId) {
        List<Long> roomIds = roomUserRepository.findByUserId(userId)
                .stream()
                .map(RoomUser::getRoomId)
                .toList();
        return roomIds.isEmpty()
                ? Collections.emptyList()
                : roomRepository.selectByIds(roomIds);
    }

    @Override
    public List<RoomUser> getRoomUsers(long roomId) {
        return roomUserRepository.find(roomId);
    }

    @Override
    public RoomUser getRoomUserInfo(long roomId, long userId) {
        return roomUserRepository.find(roomId, userId)
                .orElseThrow(() -> new BaseException(ErrorEnum.ROOM_USER_NOT_EXIST));
    }

    @Override
    public boolean isRoomMember(long roomId, long userId) {
        return roomUserRepository.find(roomId, userId).isPresent();
    }
}

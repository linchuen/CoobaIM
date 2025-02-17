package com.cooba.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.BehaviorLayer;
import com.cooba.aop.UserThreadLocal;
import com.cooba.constant.ErrorEnum;
import com.cooba.constant.RoomRoleEnum;
import com.cooba.entity.Room;
import com.cooba.entity.RoomUser;
import com.cooba.exception.BaseException;
import com.cooba.repository.RoomRepository;
import com.cooba.repository.RoomUserRepository;
import com.cooba.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomUserRepository roomUserRepository;
    private final UserThreadLocal userThreadLocal;

    @Override
    public long build(Room room, List<Long> userIds) {
        roomRepository.insert(room);

        RoomUser roomMaster = new RoomUser();
        roomMaster.setUserId(userThreadLocal.getCurrentUserId());
        roomMaster.setRoomId(room.getId());
        roomMaster.setRoomRoleEnum(RoomRoleEnum.MASTER);

        List<RoomUser> roomUsers = userIds.stream().map(userId -> {
            RoomUser roomUser = new RoomUser();
            roomUser.setUserId(userId);
            roomUser.setRoomId(room.getId());
            roomUser.setRoomRoleEnum(RoomRoleEnum.MEMBER);
            return roomUser;
        }).collect(Collectors.toList());

        roomUsers.add(roomMaster);
        roomUserRepository.insert(roomUsers);
        return room.getId();
    }

    @Override
    public void destroy(long roomId) {
        roomRepository.deleteById(roomId);

        roomUserRepository.delete(new LambdaQueryWrapper<RoomUser>()
                .eq(RoomUser::getRoomId, roomId));
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

        roomUserRepository.delete(new LambdaQueryWrapper<RoomUser>()
                .eq(RoomUser::getRoomId, roomUser.getRoomId())
                .eq(RoomUser::getUserId, roomUser.getUserId()));
    }

    @Override
    public void deleteUsers(long roomId, List<Long> userIds) {
        roomUserRepository.delete(new LambdaQueryWrapper<RoomUser>()
                .eq(RoomUser::getRoomId, roomId)
                .in(RoomUser::getUserId, userIds));
    }

    @Override
    public List<Room> searchRooms(long userId, List<Long> roomIds) {
        if(roomIds.isEmpty()){
            return roomRepository.selectList(new LambdaQueryWrapper<>());
        }

        return roomRepository.selectList(new LambdaQueryWrapper<Room>()
                .in(Room::getId, roomIds));
    }

    @Override
    public List<RoomUser> getRoomUsers(long roomId) {
        return roomUserRepository.selectList(new LambdaQueryWrapper<RoomUser>()
                .eq(RoomUser::getRoomId, roomId));
    }

    @Override
    public RoomUser getRoomUserInfo(long roomId, long userId) {
        RoomUser roomUser = roomUserRepository.selectOne(new LambdaQueryWrapper<RoomUser>()
                .eq(RoomUser::getRoomId, roomId)
                .eq(RoomUser::getUserId, userId));

        if (roomUser == null) throw new BaseException(ErrorEnum.ROOM_USER_NOT_EXIST);
        return roomUser;
    }

    @Override
    public boolean isRoomMember(long roomId, long userId) {
        RoomUser roomUser = roomUserRepository.selectOne(new LambdaQueryWrapper<RoomUser>()
                .eq(RoomUser::getRoomId, roomId)
                .eq(RoomUser::getUserId, userId));

        return roomUser != null;
    }
}

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

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomUserRepository roomUserRepository;

    @Override
    public long build(Room room) {
        roomRepository.insert(room);

        RoomUser roomUser = new RoomUser();
        roomUser.setUserId(UserThreadLocal.get().getId());
        roomUser.setRoomId(room.getId());
        roomUser.setRoomRoleEnum(RoomRoleEnum.MASTER);
        roomUserRepository.insert(roomUser);
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

        roomUserRepository.deleteById(roomUser);
    }

    @Override
    public void deleteUsers(long roomId, List<Long> userIds) {
        roomUserRepository.delete(new LambdaQueryWrapper<RoomUser>()
                .eq(RoomUser::getRoomId, roomId)
                .in(RoomUser::getUserId,userIds));
    }

    @Override
    public RoomUser getRoomUserInfo(long roomId, long userId) {
        RoomUser roomUser = roomUserRepository.selectOne(new LambdaQueryWrapper<RoomUser>()
                .eq(RoomUser::getRoomId, roomId)
                .eq(RoomUser::getUserId, userId));

        if (roomUser == null) throw new BaseException(ErrorEnum.ROOM_USER_NOT_EXIST);
        return roomUser;
    }
}

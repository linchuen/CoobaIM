package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.aop.UserThreadLocal;
import com.cooba.component.RoomComponent;
import com.cooba.constant.ErrorEnum;
import com.cooba.constant.RoomRoleEnum;
import com.cooba.dto.SendMessage;
import com.cooba.dto.request.RoomRequest;
import com.cooba.dto.request.RoomUserRequest;
import com.cooba.dto.response.BuildRoomResponse;
import com.cooba.dto.response.DestroyRoomResponse;
import com.cooba.entity.Room;
import com.cooba.entity.RoomUser;
import com.cooba.entity.User;
import com.cooba.exception.BaseException;
import com.cooba.service.MessageService;
import com.cooba.service.RoomService;
import com.cooba.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

@Slf4j
@ObjectLayer
@RequiredArgsConstructor
public class RoomComponentImpl implements RoomComponent {
    private final UserService userService;
    private final RoomService roomService;
    private final MessageService messageService;

    @Override
    public BuildRoomResponse build(RoomRequest request) {
        Room room = new Room();
        room.setName(request.getName());

        long roomId = roomService.build(room);

        return BuildRoomResponse.builder()
                .roomId(roomId)
                .build();
    }

    @Override
    public DestroyRoomResponse destroy(RoomRequest request) {
        Long userId = UserThreadLocal.get().getId();
        Long roomId = request.getRoomId();
        RoomUser roomUser = roomService.getRoomUserInfo(roomId, userId);
        if (roomUser.getRoomRoleEnum() != RoomRoleEnum.MASTER) {
            throw new BaseException(ErrorEnum.INVALID_AUTHORIZATION);
        }

        roomService.destroy(roomId);
        return DestroyRoomResponse.builder()
                .roomId(roomId)
                .build();
    }

    @Override
    public void invite(RoomUserRequest request) {
        User user = UserThreadLocal.get().getOrigin();

        RoomUser roomUser = roomService.getRoomUserInfo(request.getRoomId(), user.getId());
        RoomRoleEnum roomRole = roomUser.getRoomRoleEnum();
        if (roomRole != RoomRoleEnum.MASTER && roomRole != RoomRoleEnum.MANAGER) {
            throw new BaseException(ErrorEnum.INVALID_AUTHORIZATION);
        }

        User newUser = userService.getInfo(request.getUserId());

        RoomUser newRoomUser = new RoomUser();
        newRoomUser.setRoomId(request.getRoomId());
        newRoomUser.setUserId(request.getUserId());
        roomService.addUser(newRoomUser);

        SendMessage message = new SendMessage();
        message.setRoomId(request.getRoomId());
        message.setUser(newUser);
        message.setMessage(newUser.getName() + "進入聊天室");
        messageService.sendToRoom(message);
    }

    @Override
    public void evict(RoomUserRequest request) {
        User user = userService.getInfo(request.getUserId());

        RoomUser roomUser = new RoomUser();
        BeanUtils.copyProperties(request, roomUser);
        roomService.deleteUser(roomUser);

        SendMessage message = new SendMessage();
        message.setRoomId(request.getRoomId());
        message.setUser(user);
        message.setMessage("離開聊天室");
        messageService.sendToRoom(message);
    }
}

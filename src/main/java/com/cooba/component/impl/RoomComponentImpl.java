package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.component.RoomComponent;
import com.cooba.dto.SendMessage;
import com.cooba.dto.request.RoomRequest;
import com.cooba.dto.request.RoomUserRequest;
import com.cooba.entity.Room;
import com.cooba.entity.RoomUser;
import com.cooba.entity.User;
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
    public void build(RoomRequest request) {
        Room room = new Room();
        BeanUtils.copyProperties(request, room);

        roomService.build(room);
    }

    @Override
    public void destroy(RoomRequest request) {
        Room room = new Room();
        BeanUtils.copyProperties(request, room);

        roomService.destroy(room);
    }

    @Override
    public void invite(RoomUserRequest request) {
        User user = userService.getInfo(request.getUserId());

        RoomUser roomUser = new RoomUser();
        BeanUtils.copyProperties(request, roomUser);
        roomService.addUser(roomUser);

        SendMessage message = new SendMessage();
        message.setRoomId(request.getRoomId());
        message.setUser(user);
        message.setMessage("進入聊天室");
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

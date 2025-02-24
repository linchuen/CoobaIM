package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.aop.UserThreadLocal;
import com.cooba.component.ChatComponent;
import com.cooba.constant.ErrorEnum;
import com.cooba.dto.NotifyMessage;
import com.cooba.dto.SendMessage;
import com.cooba.dto.request.ChatLoadRequest;
import com.cooba.dto.request.SpeakRequest;
import com.cooba.dto.response.ChatLoadResponse;
import com.cooba.entity.Chat;
import com.cooba.entity.RoomUser;
import com.cooba.entity.User;
import com.cooba.exception.BaseException;
import com.cooba.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ObjectLayer
@RequiredArgsConstructor
public class ChatComponentImpl implements ChatComponent {
    private final RoomService roomService;
    private final MessageService messageService;
    private final UserThreadLocal userThreadLocal;

    @Override
    public void speakToUser(SpeakRequest request) {
        Long userId = request.getUserId();

        RoomUser roomUserInfo = roomService.getRoomUserInfo(request.getRoomId(), userId);

        SendMessage message = new SendMessage();
        message.setMessage(request.getMessage());
        message.setUserId(roomUserInfo.getUserId());
        message.setName(roomUserInfo.getShowName());
        message.setRoomId(roomUserInfo.getRoomId());

        messageService.sendToUser(message);
    }

    @Override
    public void speakToRoom(SpeakRequest request) {
        Long userId = request.getUserId();

        RoomUser roomUserInfo = roomService.getRoomUserInfo(request.getRoomId(), userId);

        SendMessage message = new SendMessage();
        message.setMessage(request.getMessage());
        message.setUserId(roomUserInfo.getUserId());
        message.setName(roomUserInfo.getShowName());
        message.setRoomId(roomUserInfo.getRoomId());

        messageService.sendToRoom(message);
    }

    @Override
    public void speakToAll(SpeakRequest request) {
        NotifyMessage message = new NotifyMessage();
        message.setUserId(request.getUserId());
        message.setMessage(request.getMessage());

        messageService.sendToAll(message);
    }

    @Override
    public ChatLoadResponse load(ChatLoadRequest request) {
        long userId = userThreadLocal.getCurrentUserId();

        boolean isRoomMember = roomService.isRoomMember(request.getRoomId(), userId);
        if (!isRoomMember) throw new BaseException(ErrorEnum.FORBIDDEN);

        List<Chat> chats = messageService.getRoomChats(request.getRoomId());
        return ChatLoadResponse.builder()
                .chats(chats)
                .build();
    }
}

package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.aop.UserThreadLocal;
import com.cooba.component.ChatComponent;
import com.cooba.constant.ErrorEnum;
import com.cooba.constant.MessageTypeEnum;
import com.cooba.dto.LastChatAndUnRead;
import com.cooba.dto.NotifyMessage;
import com.cooba.dto.SendMessage;
import com.cooba.dto.request.ChatLoadLastAndUnReadRequest;
import com.cooba.dto.request.ChatLoadRequest;
import com.cooba.dto.request.SpeakRequest;
import com.cooba.dto.response.ChatLoadLastAndUnReadResponse;
import com.cooba.dto.response.ChatLoadResponse;
import com.cooba.entity.Chat;
import com.cooba.entity.RoomUser;
import com.cooba.exception.BaseException;
import com.cooba.service.MessageService;
import com.cooba.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

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
        message.setUuid(request.getUuid());
        message.setMessage(request.getMessage());
        message.setUserId(roomUserInfo.getUserId());
        message.setName(roomUserInfo.getShowName());
        message.setRoomId(roomUserInfo.getRoomId());
        message.setUrl(request.getUrl());
        if (request.getType() != null) {
            message.setType(MessageTypeEnum.valueOf(request.getType()));
        }

        messageService.sendToUser(message);
    }

    @Override
    public void speakToRoom(SpeakRequest request) {
        Long userId = request.getUserId();

        RoomUser roomUserInfo = roomService.getRoomUserInfo(request.getRoomId(), userId);

        SendMessage message = new SendMessage();
        message.setUuid(request.getUuid());
        message.setMessage(request.getMessage());
        message.setUserId(roomUserInfo.getUserId());
        message.setName(roomUserInfo.getShowName());
        message.setRoomId(roomUserInfo.getRoomId());
        message.setUrl(request.getUrl());
        if (request.getType() != null) {
            message.setType(MessageTypeEnum.valueOf(request.getType()));
        }

        messageService.sendToRoom(message);
    }

    @Override
    public void speakToAll(SpeakRequest request) {
        NotifyMessage message = new NotifyMessage();
        message.setUuid(request.getUuid());
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

    @Override
    public ChatLoadLastAndUnReadResponse loadLastChatAndUnreadCount(ChatLoadLastAndUnReadRequest request) {
        List<Long> roomIds = request.getRoomIds();
        List<LastChatAndUnRead> chatAndUnReads = roomIds.stream().map(roomId -> {
            long roomUnread = messageService.getRoomUnread(roomId);
            Optional<Chat> lastChat = messageService.getLastChat(roomId);

            LastChatAndUnRead lastChatAndUnRead = new LastChatAndUnRead();
            lastChatAndUnRead.setUnread(roomUnread);
            lastChatAndUnRead.setChat(lastChat.orElse(null));
            lastChatAndUnRead.setRoomId(roomId);
            return lastChatAndUnRead;
        }).toList();

        return ChatLoadLastAndUnReadResponse.builder()
                .chatAndUnReads(chatAndUnReads)
                .build();
    }
}

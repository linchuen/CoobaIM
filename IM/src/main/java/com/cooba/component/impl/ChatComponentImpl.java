package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.aop.UserThreadLocal;
import com.cooba.component.ChatComponent;
import com.cooba.constant.ErrorEnum;
import com.cooba.constant.MessageTypeEnum;
import com.cooba.dto.LastChatAndUnRead;
import com.cooba.dto.NotifyMessage;
import com.cooba.dto.SendMessage;
import com.cooba.dto.request.*;
import com.cooba.dto.response.ChatLoadLastAndUnReadResponse;
import com.cooba.dto.response.ChatLoadResponse;
import com.cooba.entity.Chat;
import com.cooba.entity.Room;
import com.cooba.entity.RoomUser;
import com.cooba.exception.BaseException;
import com.cooba.service.MessageService;
import com.cooba.service.OfflineMessageService;
import com.cooba.service.RoomService;
import com.cooba.util.ConnectionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@ObjectLayer
@RequiredArgsConstructor
public class ChatComponentImpl implements ChatComponent {
    private final RoomService roomService;
    private final MessageService messageService;
    private final OfflineMessageService offlineMessageService;
    private final UserThreadLocal userThreadLocal;
    private final ConnectionManager connectionManager;

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
        offlineMessageService.sendOffline(userId, message);
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
        offlineMessageService.sendOffline(userId, message);
    }

    @Override
    public void speakToAll(SpeakRequest request) {
        NotifyMessage message = new NotifyMessage();
        message.setUuid(request.getUuid());
        message.setUserId(request.getUserId());
        message.setMessage(request.getMessage());

        messageService.sendToAll(message);
        offlineMessageService.sendToAll(request.getMessage());
    }

    @Override
    public ChatLoadResponse load(ChatLoadRequest request) {
        long userId = userThreadLocal.getCurrentUserId();

        boolean isRoomMember = roomService.isRoomMember(request.getRoomId(), userId);
        if (!isRoomMember) throw new BaseException(ErrorEnum.FORBIDDEN);

        connectionManager.addUser(String.valueOf(userId), "");

        List<Chat> chats = messageService.getRoomChats(request.getRoomId(), request.getChatId(), request.isSearchAfter());
        return ChatLoadResponse.builder()
                .chats(chats)
                .build();
    }

    @Override
    public ChatLoadLastAndUnReadResponse loadLastChatAndUnreadCount(ChatLoadLastAndUnReadRequest request) {
        long userId = userThreadLocal.getCurrentUserId();

        List<Long> roomIds = !request.getRoomIds().isEmpty()
                ? request.getRoomIds()
                : roomService.searchRooms(userId).stream().map(Room::getId).toList();
        List<LastChatAndUnRead> chatAndUnReads = roomIds.stream().map(roomId -> {
            long roomUnread = messageService.getRoomUnread(roomId);
            Optional<Chat> lastChat = messageService.getLastChat(roomId);

            LastChatAndUnRead lastChatAndUnRead = new LastChatAndUnRead();
            lastChatAndUnRead.setUnread(roomUnread);
            lastChatAndUnRead.setChat(lastChat.orElse(new Chat()));
            lastChatAndUnRead.setRoomId(roomId);
            return lastChatAndUnRead;
        }).toList();

        return ChatLoadLastAndUnReadResponse.builder()
                .chatAndUnReads(chatAndUnReads)
                .build();
    }

    @Override
    public void setIsRead(ChatIsReadRequest request) {
        messageService.setRoomIsRead(request.getRoomId(), request.getChatId());
    }

    @Override
    public ChatLoadResponse loadByDate(ChatLoadDateRequest request) {
        long userId = userThreadLocal.getCurrentUserId();

        boolean isRoomMember = roomService.isRoomMember(request.getRoomId(), userId);
        if (!isRoomMember) throw new BaseException(ErrorEnum.FORBIDDEN);

        List<Chat> chats;
        LocalDate date = request.getDate();
        if (date != null) {
            chats = messageService.getRoomChats(request.getRoomId(), date);
        } else {
            chats = messageService.getRoomChats(request.getRoomId(), request.getStartTime(), request.getEndTime());
        }
        return ChatLoadResponse.builder()
                .chats(chats)
                .build();
    }

    @Override
    public ChatLoadResponse searchWord(ChatSearchRequest request) {
        long userId = userThreadLocal.getCurrentUserId();

        boolean isRoomMember = roomService.isRoomMember(request.getRoomId(), userId);
        if (!isRoomMember) throw new BaseException(ErrorEnum.FORBIDDEN);

        List<Chat> chats = messageService.searchWord(request.getRoomId(), request.getWord());
        return ChatLoadResponse.builder()
                .chats(chats)
                .build();
    }
}

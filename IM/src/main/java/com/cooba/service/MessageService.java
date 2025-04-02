package com.cooba.service;

import com.cooba.dto.NotifyMessage;
import com.cooba.dto.SendMessage;
import com.cooba.entity.Chat;
import com.cooba.entity.ChatSearch;
import com.cooba.entity.Notification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MessageService {
    void sendToUser(SendMessage message);

    void sendToRoom(SendMessage message);

    void sendToAll(NotifyMessage message);

    List<Chat> getRoomChats(long roomId);

    List<Chat> getRoomChats(long roomId, Long chatId, boolean searchAfter);

    List<Chat> getRoomChats(long roomId, LocalDateTime startTime, LocalDateTime endTime);

    List<Chat> getRoomChats(long roomId, LocalDate date);

    List<ChatSearch> insertMessageGram(Chat chat);

    List<Chat> searchWord(long roomId, String word);

    void setRoomIsRead(long roomId, long chatId);

    long getRoomUnread(long roomId);

    Optional<Chat> getLastChat(long roomId);

    List<Notification> getNotifications();
}

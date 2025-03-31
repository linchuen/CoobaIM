package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.aop.UserThreadLocal;
import com.cooba.core.SocketConnection;
import com.cooba.dto.NotifyMessage;
import com.cooba.dto.SendMessage;
import com.cooba.entity.Chat;
import com.cooba.entity.ChatRead;
import com.cooba.entity.ChatSearch;
import com.cooba.entity.Notification;
import com.cooba.repository.ChatReadRepository;
import com.cooba.repository.ChatRepository;
import com.cooba.repository.ChatSearchRepository;
import com.cooba.repository.NotificationRepository;
import com.cooba.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final ChatRepository chatRepository;
    private final ChatReadRepository chatReadRepository;
    private final ChatSearchRepository chatSearchRepository;
    private final NotificationRepository notificationRepository;
    private final SocketConnection socketConnection;
    private final UserThreadLocal userThreadLocal;

    @Override
    public void sendToUser(SendMessage message) {
        Chat chat = new Chat(message);

        chatRepository.insert(chat);
        chatRepository.insertLastChat(chat);
        chatSearchRepository.insertMessageGram(chat);

        socketConnection.sendToGroup(String.valueOf(chat.getRoomId()), chat);
    }

    @Override
    public void sendToRoom(SendMessage message) {
        Chat chat = new Chat(message);

        chatRepository.insert(chat);
        chatRepository.insertLastChat(chat);
        chatSearchRepository.insertMessageGram(chat);

        socketConnection.sendToGroup(String.valueOf(chat.getRoomId()), chat);
    }

    @Override
    public void sendToAll(NotifyMessage message) {
        Notification notification = new Notification();
        notification.setUserId(message.getUserId());
        notification.setMessage(message.getMessage());

        notificationRepository.insert(notification);

        socketConnection.sendToAll(notification.getMessage());
    }

    @Override
    public List<Chat> getRoomChats(long roomId) {
        return chatRepository.findChatByRoomId(roomId, null, false);
    }

    @Override
    public List<Chat> getRoomChats(long roomId, Long chatId, boolean searchAfter) {
        return chatRepository.findChatByRoomId(roomId, chatId, searchAfter);
    }

    @Override
    public List<Chat> getRoomChats(long roomId, LocalDateTime startTime, LocalDateTime endTime) {
        return chatRepository.findChatByRoomId(roomId, startTime, endTime);
    }

    @Override
    public List<Chat> getRoomChats(long roomId, LocalDate date) {
        return chatRepository.findChatByRoomId(roomId, date);
    }

    @Override
    public List<Chat> searchWord(long roomId, String word) {
        List<ChatSearch> byWords = chatSearchRepository.findByWord(roomId, word);
        List<Long> chatIds = byWords.stream().map(ChatSearch::getChatId).distinct().toList();
        return chatRepository.selectByIds(chatIds);
    }

    @Override
    public void setRoomIsRead(long roomId, long chatId) {
        long userId = userThreadLocal.getCurrentUserId();

        ChatRead chatRead = new ChatRead();
        chatRead.setRoomId(roomId);
        chatRead.setUserId(userId);
        chatRead.setChatId(chatId);
        chatReadRepository.insert(chatRead);
    }

    @Override
    public long getRoomUnread(long roomId) {
        long userId = userThreadLocal.getCurrentUserId();

        long chatId = chatReadRepository.findTopByOrderByIdDesc(roomId, userId);
        return chatRepository.countChatByChatId(roomId, chatId);
    }

    @Override
    public Optional<Chat> getLastChat(long roomId) {
        return chatRepository.findLastChatByRoomId(roomId);
    }

    @Override
    public List<Notification> getNotifications() {
        return notificationRepository.findAll();
    }
}

package com.cooba.repository;

import com.cooba.entity.Chat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ChatRepository extends BaseRepository<Chat> {

    List<Chat> findChatByRoomId(long roomId, Long chatId, boolean searchAfter);

    List<Chat> findChatByRoomId(long roomId, LocalDate date);

    List<Chat> findChatByRoomId(long roomId, LocalDateTime startTime, LocalDateTime endTime);

    long countChatByChatId(long roomId, long chatId);

    Optional<Chat> findLastChatByRoomId(long roomId);

    void insertLastChat(Chat chat);
}

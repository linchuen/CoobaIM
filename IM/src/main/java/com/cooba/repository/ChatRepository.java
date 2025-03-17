package com.cooba.repository;

import com.cooba.entity.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends BaseRepository<Chat> {

    List<Chat> findChatByRoomId(long roomId);

    long countChatByChatId(long roomId, long chatId);

    Optional<Chat> findLastChatByRoomId(long roomId);

    void insertLastChat(Chat chat);
}

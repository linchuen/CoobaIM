package com.cooba.repository;

import com.cooba.entity.Chat;

import java.util.List;

public interface ChatRepository extends BaseRepository<Chat>{

    List<Chat> findChatByRoomId(long roomId);
}

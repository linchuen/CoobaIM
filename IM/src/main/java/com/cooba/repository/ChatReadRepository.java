package com.cooba.repository;

import com.cooba.entity.ChatRead;

public interface ChatReadRepository extends BaseRepository<ChatRead> {

    long findTopByOrderByIdDesc(long roomId, long userId);
}

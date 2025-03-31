package com.cooba.repository;

import com.cooba.entity.ChatSearch;

import java.util.List;

public interface ChatSearchRepository extends BaseRepository<ChatSearch> {

    List<ChatSearch> findByWord(long roomId, String word);
}

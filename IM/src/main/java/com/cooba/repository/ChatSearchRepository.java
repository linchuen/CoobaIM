package com.cooba.repository;

import com.cooba.entity.Chat;
import com.cooba.entity.ChatSearch;

import java.util.List;

public interface ChatSearchRepository extends BaseRepository<ChatSearch> {

    List<ChatSearch> findByWord(long roomId, String word);

    List<ChatSearch>  insertMessageGram(Chat chat);
}

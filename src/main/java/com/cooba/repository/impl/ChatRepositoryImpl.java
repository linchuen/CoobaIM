package com.cooba.repository.impl;

import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.Chat;
import com.cooba.mapper.ChatMapper;
import com.cooba.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepository {
    private final ChatMapper chatMapper;

    @Override
    public void insert(Chat chat) {
        chatMapper.insert(chat);
    }


}

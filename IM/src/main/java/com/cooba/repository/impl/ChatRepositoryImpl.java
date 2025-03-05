package com.cooba.repository.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.DataManipulateLayer;
import com.cooba.constant.Database;
import com.cooba.entity.Chat;
import com.cooba.mapper.ChatMapper;
import com.cooba.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
@DS(Database.clickhouse)
public class ChatRepositoryImpl implements ChatRepository {
    private final ChatMapper chatMapper;

    @Override
    public void insert(Chat chat) {
        chatMapper.insert(chat);
    }

    @Override
    public void insert(List<Chat> chats) {
        chatMapper.insert(chats);
    }

    @Override
    public Chat selectById(long id) {
        return chatMapper.selectById(id);
    }

    @Override
    public List<Chat> selectByIds(List<Long> ids) {
        return chatMapper.selectByIds(ids);
    }

    @Override
    public void deleteById(long id) {
        chatMapper.deleteById(id);
    }


    @Override
    public List<Chat> findChatByRoomId(long roomId) {
        return chatMapper.selectList(new LambdaQueryWrapper<Chat>()
                .eq(Chat::getRoomId, roomId));
    }
}

package com.cooba.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.ChatRead;
import com.cooba.mapper.ChatReadMapper;
import com.cooba.repository.ChatReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
public class ChatReadRepositoryImpl implements ChatReadRepository {
    private final ChatReadMapper chatReadMapper;

    @Override
    public void insert(ChatRead chatRead) {
        chatReadMapper.insert(chatRead);
    }

    @Override
    public void insert(List<ChatRead> t) {
        chatReadMapper.insert(t);
    }

    @Override
    public ChatRead selectById(long id) {
        return chatReadMapper.selectById(id);
    }

    @Override
    public List<ChatRead> selectByIds(List<Long> ids) {
        return chatReadMapper.selectByIds(ids);
    }

    @Override
    public void deleteById(long id) {
        chatReadMapper.deleteById(id);
    }


    @Override
    public long findTopByOrderByIdDesc(long roomId, long userId) {
        ChatRead chatRead = chatReadMapper.selectOne(new LambdaQueryWrapper<ChatRead>()
                .eq(ChatRead::getRoomId, roomId)
                .eq(ChatRead::getUserId, userId)
                .orderByDesc(ChatRead::getId)
                .last("limit 1")
        );
        return chatRead == null ? 0L : chatRead.getChatId();
    }
}

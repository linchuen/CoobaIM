package com.cooba.repository.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.DataManipulateLayer;
import com.cooba.constant.Database;
import com.cooba.entity.Chat;
import com.cooba.mapper.ChatMapper;
import com.cooba.repository.ChatRepository;
import com.cooba.util.cache.CacheUtil;
import com.cooba.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
@DS(Database.clickhouse)
public class ChatRepositoryImpl implements ChatRepository {
    private final ChatMapper chatMapper;
    private final CacheUtil cacheUtil;

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
    public List<Chat> findChatByRoomId(long roomId, Long chatId, boolean searchAfter) {
        //for clickhouse
        return chatMapper.selectList(new LambdaQueryWrapper<Chat>()
                .eq(Chat::getRoomId, roomId)
                .gt(searchAfter && chatId != null, Chat::getId, chatId)
                .lt(!searchAfter && chatId != null, Chat::getId, chatId)
                .orderByDesc(Chat::getId)
                .last("limit 100")
        );
    }

    @Override
    public List<Chat> findChatByRoomId(long roomId, LocalDate date) {
        LocalDateTime startTime = date.atStartOfDay();
        LocalDateTime endTime = startTime.plusDays(1);
        return findChatByRoomId(roomId, startTime, endTime);
    }

    @Override
    public List<Chat> findChatByRoomId(long roomId, LocalDateTime startTime, LocalDateTime endTime) {
        return chatMapper.selectList(new LambdaQueryWrapper<Chat>()
                .eq(Chat::getRoomId, roomId)
                .ge(Chat::getCreatedTime, startTime)
                .le(Chat::getCreatedTime, endTime)
                .orderByDesc(Chat::getId)
                .last("limit 100")
        );
    }

    @Override
    public long countChatByChatId(long roomId, long chatId) {
        if (chatId == 0) return 0;

        return chatMapper.selectCount(new LambdaQueryWrapper<Chat>()
                .eq(Chat::getRoomId, roomId)
                .gt(Chat::getId, chatId));
    }

    @Override
    public Optional<Chat> findLastChatByRoomId(long roomId) {
        String json = cacheUtil.get("chat:" + roomId);
        if (json != null) return Optional.of(JsonUtil.fromJson(json, Chat.class));

        Chat chat = chatMapper.selectOne(new LambdaQueryWrapper<Chat>()
                .eq(Chat::getRoomId, roomId)
                .orderByDesc(Chat::getId)
                .last("limit 1")
        );
        if (chat == null) return Optional.empty();
        return Optional.of(chat);
    }

    @Override
    public void insertLastChat(Chat chat) {
        cacheUtil.set("chat:" + chat.getRoomId(), JsonUtil.toJson(chat), Duration.ofDays(30));
    }
}

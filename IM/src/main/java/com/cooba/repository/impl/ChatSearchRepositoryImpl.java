package com.cooba.repository.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.DataManipulateLayer;
import com.cooba.constant.Database;
import com.cooba.constant.MessageTypeEnum;
import com.cooba.entity.Chat;
import com.cooba.entity.ChatSearch;
import com.cooba.mapper.ChatSearchMapper;
import com.cooba.repository.ChatSearchRepository;
import com.cooba.util.NgramUtil;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
@DS(Database.clickhouse)
public class ChatSearchRepositoryImpl implements ChatSearchRepository {
    private final ChatSearchMapper chatSearchMapper;

    @Override
    public void insert(ChatSearch chatSearch) {
        chatSearchMapper.insert(chatSearch);
    }

    @Override
    public void insert(List<ChatSearch> t) {
        chatSearchMapper.insert(t);
    }

    @Override
    public ChatSearch selectById(long id) {
        return chatSearchMapper.selectById(id);
    }

    @Override
    public List<ChatSearch> selectByIds(List<Long> ids) {
        return chatSearchMapper.selectByIds(ids);
    }

    @Override
    public void deleteById(long id) {
        chatSearchMapper.deleteById(id);
    }

    @Override
    public List<ChatSearch> findByWord(long roomId, String word) {
        LocalDateTime oneMonth = LocalDateTime.now().minusMonths(1);
        return chatSearchMapper.selectList(new LambdaQueryWrapper<ChatSearch>()
                .eq(ChatSearch::getRoomId, roomId)
                .eq(ChatSearch::getMessageGram, ZhConverterUtil.toSimple(word.toLowerCase()))
                .gt(ChatSearch::getCreatedTime, oneMonth)
        );
    }

    @Async
    @Override
    public List<ChatSearch> insertMessageGram(Chat chat) {
        List<ChatSearch> chatSearches = NgramUtil.generate2gramsWord(chat.getMessage())
                .stream()
                .map(s -> {
                    ChatSearch chatSearch = new ChatSearch();
                    chatSearch.setRoomId(chat.getRoomId());
                    chatSearch.setMessageGram(s.toLowerCase());
                    chatSearch.setChatId(chat.getId());
                    return chatSearch;
                }).toList();

        chatSearchMapper.insert(chatSearches);
        return chatSearches;
    }
}

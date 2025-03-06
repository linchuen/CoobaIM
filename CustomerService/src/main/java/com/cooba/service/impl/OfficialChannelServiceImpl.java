package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.entity.OfficialChannel;
import com.cooba.mapper.OfficialChannelMapper;
import com.cooba.repository.OfficialChannelRepository;
import com.cooba.service.OfficialChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class OfficialChannelServiceImpl implements OfficialChannelService {
    private final OfficialChannelRepository officialChannelRepository;
    @Override
    public long create(OfficialChannel officialChannel) {
        officialChannelRepository.insert(officialChannel);
        return officialChannel.getId();
    }

    @Override
    public void delete(long channelId) {
        officialChannelRepository.selectById(channelId);
    }

    @Override
    public List<OfficialChannel> searchAll() {
        return officialChannelRepository.selectByIds(Collections.emptyList());
    }
}

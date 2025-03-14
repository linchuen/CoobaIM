package com.cooba.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.OfficialChannel;
import com.cooba.mapper.OfficialChannelMapper;
import com.cooba.repository.OfficialChannelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
public class OfficialChannelRepositoryImpl implements OfficialChannelRepository {
    private final OfficialChannelMapper officialChannelMapper;

    @Override
    public void insert(OfficialChannel officialChannel) {
        officialChannelMapper.insert(officialChannel);
    }

    @Override
    public void insert(List<OfficialChannel> t) {
        officialChannelMapper.insert(t);
    }

    @Override
    public OfficialChannel selectById(long id) {
        return officialChannelMapper.selectById(id);
    }

    @Override
    public List<OfficialChannel> selectByIds(List<Long> ids) {
        return officialChannelMapper.selectList(new LambdaQueryWrapper<OfficialChannel>()
                .in(!ids.isEmpty(), OfficialChannel::getId, ids));
    }

    @Override
    public void deleteById(long id) {
        officialChannelMapper.deleteById(id);
    }
}

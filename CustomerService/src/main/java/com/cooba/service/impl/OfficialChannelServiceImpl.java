package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.aop.UserThreadLocal;
import com.cooba.constant.RoleEnum;
import com.cooba.entity.OfficialChannel;
import com.cooba.entity.User;
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
    private final UserThreadLocal userThreadLocal;

    @Override
    public Long create(OfficialChannel officialChannel) {
        officialChannelRepository.insert(officialChannel);
        return officialChannel.getId();
    }

    @Override
    public void delete(long channelId) {
        officialChannelRepository.deleteById(channelId);
    }

    @Override
    public List<OfficialChannel> searchAll() {
        User currentUser = userThreadLocal.getCurrentUser();
        boolean isGuest = currentUser.getRole().equals(RoleEnum.GUEST.getRole());

        return officialChannelRepository.selectByIds(Collections.emptyList())
                .stream()
                .filter(officialChannel -> !isGuest || officialChannel.isPublic())
                .toList();
    }
}

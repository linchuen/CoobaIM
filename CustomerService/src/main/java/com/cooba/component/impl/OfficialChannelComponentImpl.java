package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.aop.UserThreadLocal;
import com.cooba.component.OfficialChannelComponent;
import com.cooba.constant.RoleEnum;
import com.cooba.dto.request.ChannelCreateRequest;
import com.cooba.dto.request.ChannelDeleteRequest;
import com.cooba.dto.response.ChannelCreateResponse;
import com.cooba.dto.response.ChannelDeleteResponse;
import com.cooba.dto.response.ChannelSearchResponse;
import com.cooba.entity.OfficialChannel;
import com.cooba.entity.User;
import com.cooba.service.OfficialChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ObjectLayer
@RequiredArgsConstructor
public class OfficialChannelComponentImpl implements OfficialChannelComponent {
    private final OfficialChannelService officialChannelService;

    @Override
    public ChannelCreateResponse create(ChannelCreateRequest request) {
        OfficialChannel officialChannel = new OfficialChannel();
        officialChannel.setName(request.getName());
        officialChannel.setPublic(request.isPublic());
        officialChannelService.create(officialChannel);

        return ChannelCreateResponse.builder()
                .channelId(officialChannel.getId())
                .build();
    }

    @Override
    public ChannelDeleteResponse delete(ChannelDeleteRequest request) {
        officialChannelService.delete(request.getChannelId());

        return ChannelDeleteResponse.builder()
                .channelId(request.getChannelId())
                .build();
    }

    @Override
    public ChannelSearchResponse search() {
        List<OfficialChannel> officialChannels = officialChannelService.searchAll();
        return ChannelSearchResponse.builder()
                .channels(officialChannels)
                .build();
    }
}

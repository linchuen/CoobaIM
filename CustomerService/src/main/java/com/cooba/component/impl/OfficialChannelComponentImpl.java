package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.aop.UserThreadLocal;
import com.cooba.component.OfficialChannelComponent;
import com.cooba.constant.RoleEnum;
import com.cooba.constatnt.CsEventEnum;
import com.cooba.core.SocketConnection;
import com.cooba.dto.request.ChannelCreateRequest;
import com.cooba.dto.request.ChannelDeleteRequest;
import com.cooba.dto.request.ChannelUpdateRequest;
import com.cooba.dto.response.ChannelCreateResponse;
import com.cooba.dto.response.ChannelDeleteResponse;
import com.cooba.dto.response.ChannelSearchResponse;
import com.cooba.entity.OfficialChannel;
import com.cooba.entity.User;
import com.cooba.service.OfficialChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@ObjectLayer
@RequiredArgsConstructor
public class OfficialChannelComponentImpl implements OfficialChannelComponent {
    private final OfficialChannelService officialChannelService;
    private final SocketConnection socketConnection;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChannelCreateResponse create(ChannelCreateRequest request) {
        OfficialChannel officialChannel = new OfficialChannel();
        officialChannel.setName(request.getName());
        officialChannel.setIsPublic(request.isPublic());
        officialChannelService.create(officialChannel);

        socketConnection.sendAllEvent(CsEventEnum.CHANNEL_ADD, officialChannel);
        return ChannelCreateResponse.builder()
                .channelId(officialChannel.getId())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ChannelUpdateRequest request) {
        OfficialChannel officialChannel = new OfficialChannel();
        officialChannel.setId(request.getChannelId());
        officialChannel.setName(request.getName());
        officialChannel.setIsPublic(request.getIsPublic());
        officialChannelService.update(officialChannel);

        socketConnection.sendAllEvent(CsEventEnum.CHANNEL_UPDATE, officialChannel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChannelDeleteResponse delete(ChannelDeleteRequest request) {
        officialChannelService.delete(request.getChannelId());

        socketConnection.sendAllEvent(CsEventEnum.CHANNEL_DELETE, request.getChannelId());
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

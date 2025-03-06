package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.component.OfficialChannelComponent;
import com.cooba.dto.request.CreateChannelRequest;
import com.cooba.dto.response.ChannelCreateResponse;
import com.cooba.dto.response.ChannelSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ObjectLayer
@RequiredArgsConstructor
public class OfficialChannelComponentImpl implements OfficialChannelComponent {
    @Override
    public ChannelCreateResponse create(CreateChannelRequest request) {


        return ChannelCreateResponse
                .builder()
                .build();
    }

    @Override
    public void delete(long channelId) {

    }

    @Override
    public ChannelSearchResponse search() {

        return ChannelSearchResponse
                .builder()
                .build();
    }
}

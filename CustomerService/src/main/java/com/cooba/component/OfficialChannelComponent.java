package com.cooba.component;

import com.cooba.dto.request.CreateChannelRequest;
import com.cooba.dto.response.ChannelCreateResponse;
import com.cooba.dto.response.ChannelSearchResponse;

public interface OfficialChannelComponent {

    ChannelCreateResponse create(CreateChannelRequest request);

    void delete(long channelId);

    ChannelSearchResponse search();
}

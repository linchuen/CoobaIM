package com.cooba.component;

import com.cooba.dto.request.ChannelCreateRequest;
import com.cooba.dto.request.ChannelDeleteRequest;
import com.cooba.dto.response.ChannelCreateResponse;
import com.cooba.dto.response.ChannelSearchResponse;

public interface OfficialChannelComponent {

    ChannelCreateResponse create(ChannelCreateRequest request);

    void delete(ChannelDeleteRequest request);

    ChannelSearchResponse search();
}

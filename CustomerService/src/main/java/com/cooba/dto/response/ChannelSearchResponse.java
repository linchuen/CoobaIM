package com.cooba.dto.response;

import com.cooba.entity.OfficialChannel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ChannelSearchResponse {
    private List<OfficialChannel> channels;
}

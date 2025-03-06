package com.cooba.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChannelDeleteRequest {
    @NotNull
    private Long channelId;
}

package com.cooba.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChannelUpdateRequest {
    @NotNull
    private Long channelId;
    @NotEmpty
    private String name;
    @NotNull
    private Boolean isPublic;
}

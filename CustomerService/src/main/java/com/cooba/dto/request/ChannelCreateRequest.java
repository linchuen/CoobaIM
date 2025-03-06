package com.cooba.dto.request;

import lombok.Data;

@Data
public class ChannelCreateRequest {
    private String name;
    private boolean isPublic = false;
}

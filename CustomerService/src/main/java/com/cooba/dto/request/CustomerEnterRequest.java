package com.cooba.dto.request;

import lombok.Data;

@Data
public class CustomerEnterRequest {
    private Long channelId;
    private boolean isUsePreviousChat = false;
}

package com.cooba.dto.request;

import lombok.Data;

@Data
public class CustomerEnterRequest {
    private final Long roomId;
    private final boolean isUsePreviousChat = false;
}

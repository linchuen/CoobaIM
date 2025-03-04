package com.cooba.dto.request;

import lombok.Data;

@Data
public class LiveCloseRequest {
    private long roomId;
    private String roomName;
}

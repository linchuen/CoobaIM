package com.cooba.dto.request;

import lombok.Data;

@Data
public class SpeakRequest {
    private Long userId;
    private Long roomId;
    private String message;
}

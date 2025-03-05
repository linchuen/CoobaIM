package com.cooba.dto.request;

import lombok.Data;

@Data
public class SpeakRequest {
    private String uuid;
    private Long roomId;
    private Long userId;
    private String message;
    private String url;
    private String type;
}

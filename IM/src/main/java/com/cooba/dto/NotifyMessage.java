package com.cooba.dto;

import lombok.Data;

@Data
public class NotifyMessage {
    private String uuid;
    private Long userId;
    private String message;
}

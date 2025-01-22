package com.cooba.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Chat {
    private Long id;
    private Long roomId;
    private Long userId;
    private String msg;
    private int version;
    private LocalDateTime createdTime;
}

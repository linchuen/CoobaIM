package com.cooba.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoomUser {
    private Long id;
    private Long roomId;
    private Long userId;
    private LocalDateTime createdTime;
}

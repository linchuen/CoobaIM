package com.cooba.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class FriendInfo {
    private Long id;
    private Long userId;
    private Long friendUserId;
    private String showName;
    private Long roomId;
    private String avatar;
    private LocalDateTime createdTime;
}

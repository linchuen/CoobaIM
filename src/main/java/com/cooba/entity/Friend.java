package com.cooba.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Friend {
    private Long Id;
    private Long applyUserId;
    private Long permitUserId;
    private Boolean isPermit;
    private LocalDateTime createdTime;
}

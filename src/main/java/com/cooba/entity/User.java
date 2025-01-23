package com.cooba.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String name;
    private LocalDateTime createdTime;
}

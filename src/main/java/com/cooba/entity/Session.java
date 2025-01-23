package com.cooba.entity;

import lombok.Data;

@Data
public class Session {
    private Long id;
    private Long userId;
    private String token;
}

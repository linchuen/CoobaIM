package com.cooba.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class LoginResponse {
    private long userId;
    private String name;
    private String token;
    private String role;
    private String platform;
    private LocalDateTime loginTime;
    private LocalDateTime expireTime;
}

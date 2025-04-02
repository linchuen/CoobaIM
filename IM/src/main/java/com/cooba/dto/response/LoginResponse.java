package com.cooba.dto.response;

import com.cooba.constant.PlatformEnum;
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
    private PlatformEnum platform;
    private String avatar;
    private LocalDateTime loginTime;
    private LocalDateTime expireTime;
}

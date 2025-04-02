package com.cooba.dto.request;

import com.cooba.constant.PlatformEnum;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
public class LoginRequest {
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotNull
    private PlatformEnum platform;

    private String ip;
}

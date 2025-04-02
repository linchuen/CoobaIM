package com.cooba.dto.request;

import com.cooba.constant.PlatformEnum;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class LogoutRequest {
    @NotNull
    private Long userId;
    @NotEmpty
    private PlatformEnum platform;
}

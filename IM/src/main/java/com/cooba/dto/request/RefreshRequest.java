package com.cooba.dto.request;

import com.cooba.constant.PlatformEnum;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RefreshRequest {
    @NotEmpty
    private PlatformEnum platform;

    private String ip;
}

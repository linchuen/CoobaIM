package com.cooba.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RefreshRequest {
    @NotEmpty
    private String platform;

    private String ip;
}

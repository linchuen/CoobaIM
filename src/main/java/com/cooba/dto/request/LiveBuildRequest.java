package com.cooba.dto.request;

import lombok.Data;

@Data
public class LiveBuildRequest {
    private long roomId;
    private String passcode;
}

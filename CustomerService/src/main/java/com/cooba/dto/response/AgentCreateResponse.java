package com.cooba.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class AgentCreateResponse {
    private Long agentId;
    private Long userId;
    private boolean isDisable;
    private LocalDateTime createdTime;
}

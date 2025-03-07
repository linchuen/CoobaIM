package com.cooba.dto.request;

import lombok.Data;

@Data
public class AgentUpdateRequest {
    private Long agentId;
    private Boolean isDisable;
}

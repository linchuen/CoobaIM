package com.cooba.dto.request;

import lombok.Data;

@Data
public class AgentUpdateRequest {
    private Long agentUserId;
    private Boolean isDisable;
}

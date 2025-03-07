package com.cooba.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class AgentSearchRequest {
    private List<Long> agentIds;
}

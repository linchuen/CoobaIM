package com.cooba.dto.response;

import com.cooba.entity.Agent;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AgentSearchResponse {
    private List<Agent> agents;
}

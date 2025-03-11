package com.cooba.dto.response;

import com.cooba.dto.AgentInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AgentSearchResponse {
    private List<AgentInfo> agents;
}

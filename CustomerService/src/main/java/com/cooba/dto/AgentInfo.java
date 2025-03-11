package com.cooba.dto;

import com.cooba.entity.Agent;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AgentInfo {
    private Long id;
    private Long userId;
    private String name;
    private Boolean isDisable;
    private Boolean isDefault;

    public AgentInfo(Agent agent, String name) {
        this.id = agent.getId();
        this.userId = agent.getUserId();
        this.name = name;
        this.isDisable = agent.isDisable();
        this.isDefault = agent.isDefault();
    }
}

package com.cooba.dto;

import com.cooba.entity.Agent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AgentInfo {
    private Long id;
    private Long userId;
    private String name;
    private Boolean isDisable;
    private Boolean isDefault;
    private String department;
    private LocalDateTime createdTime;

    public AgentInfo(Agent agent) {
        this.id = agent.getId();
        this.userId = agent.getUserId();
        this.name = agent.getName();
        this.isDisable = agent.getIsDisable();
        this.isDefault = agent.getIsDefault();
        this.department = agent.getDepartment();
        this.createdTime = agent.getCreatedTime();
    }
}

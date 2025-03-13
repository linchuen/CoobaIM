package com.cooba.dto;

import com.cooba.entity.Agent;
import com.cooba.entity.AgentCustomer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CustomerAgentInfo {
    private Long agentId;
    private Long agentUserId;
    private Long roomId;
    private String name;
    private String department;

    public CustomerAgentInfo(Agent agent, AgentCustomer agentCustomer) {
        this.agentId = agent.getId();
        this.agentUserId = agent.getUserId();
        this.roomId = agentCustomer.getRoomId();
        this.name = agent.getName();
        this.department = agent.getDepartment();
    }
}

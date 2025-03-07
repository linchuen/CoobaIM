package com.cooba.dto;

import com.cooba.entity.AgentCustomer;
import lombok.Data;

@Data
public class CustomerInfo {
    private long agentCustomerId;
    private long customerUserId;
    private String name;

    public CustomerInfo(AgentCustomer agentCustomer) {
        this.agentCustomerId = agentCustomer.getId();
        this.customerUserId = agentCustomer.getCustomerUserId();
        this.name = agentCustomer.getShowName();
    }
}

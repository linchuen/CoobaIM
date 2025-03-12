package com.cooba.dto.response;


import com.cooba.entity.AgentCustomer;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CustomerBindResponse {
    private List<AgentCustomer> agentCustomers;
}

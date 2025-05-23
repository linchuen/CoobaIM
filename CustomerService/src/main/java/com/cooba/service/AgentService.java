package com.cooba.service;

import com.cooba.dto.AgentInfo;
import com.cooba.dto.CustomerAgentInfo;
import com.cooba.entity.Agent;
import com.cooba.entity.AgentCustomer;
import com.cooba.entity.UserDetail;

import java.util.List;

public interface AgentService {

    Long create(Agent agent);

    void update(Agent agent);

    Agent search(long agentUserId);

    List<AgentInfo> search(List<Long> agentIds);

    void disable(Agent agent);

    List<AgentCustomer> searchCustomer(long agentId);

    List<CustomerAgentInfo> searchAgent(long customerUserId);

    void bindCustomer(List<AgentCustomer> agentCustomers);

    void unbindCustomer(long agentUserId, List<Long> customerUserIds);

    List<UserDetail> searchCustomerDetail();

}

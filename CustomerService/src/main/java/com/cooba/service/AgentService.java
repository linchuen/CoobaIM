package com.cooba.service;

import com.cooba.dto.AgentInfo;
import com.cooba.entity.Agent;
import com.cooba.entity.AgentCustomer;

import java.util.List;

public interface AgentService {

    long create(Agent agent);

    void update(Agent agent);

    Agent search(long agentUserId);

    List<AgentInfo> search(List<Long> agentIds);

    void disable(Agent agent);

    List<AgentCustomer> searchCustomer(long agentId);

    void bindCustomer(List<AgentCustomer> agentCustomers);

    void unbindCustomer(long agentUserId, List<Long> customerUserIds);

}

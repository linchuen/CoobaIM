package com.cooba.service;

import com.cooba.entity.Agent;
import com.cooba.entity.AgentCustomer;

import java.util.List;
import java.util.Optional;

public interface AgentService {

    long create(Agent agent);

    void update(Agent agent);

    Optional<Agent> search(long userId);

    List<Agent> search(List<Long> agentIds);

    void disable(Agent agent);

    List<AgentCustomer> searchCustomer(long agentId);

    void bindCustomer(List<AgentCustomer> agentCustomers);

    void unbindCustomer(List<Long> customerUserIds);

}

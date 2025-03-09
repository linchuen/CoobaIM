package com.cooba.repository;

import com.cooba.entity.AgentCustomer;

import java.util.List;

public interface AgentCustomerRepository extends BaseRepository<AgentCustomer> {
    List<AgentCustomer> findByAgentId(long agentUserId);

    List<AgentCustomer> findByCustomerId(long customerUserId);

    void deleteByCustomerUserIds(long agentUserId, List<Long> customerUserIds);
}

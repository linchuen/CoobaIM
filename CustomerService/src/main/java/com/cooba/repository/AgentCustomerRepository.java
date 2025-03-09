package com.cooba.repository;

import com.cooba.entity.AgentCustomer;

import java.util.List;

public interface AgentCustomerRepository extends BaseRepository<AgentCustomer> {
    List<AgentCustomer> findByAgentId(long agentId);

    void deleteByCustomerUserIds(long agentUserId, List<Long> customerUserIds);
}

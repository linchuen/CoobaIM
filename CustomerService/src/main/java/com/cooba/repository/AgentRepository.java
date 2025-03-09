package com.cooba.repository;

import com.cooba.entity.Agent;

public interface AgentRepository extends BaseRepository<Agent>{
   Agent findByUserId(long agentUserId);

    void update(Agent agent);
}

package com.cooba.repository;

import com.cooba.entity.Agent;

import java.util.List;

public interface AgentRepository extends BaseRepository<Agent>{
   Agent findByUserId(long agentUserId);

    List<Agent> findByDefault();

    void update(Agent agent);
}

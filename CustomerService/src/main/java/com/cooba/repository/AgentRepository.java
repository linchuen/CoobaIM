package com.cooba.repository;

import com.cooba.entity.Agent;
import com.cooba.entity.UserDetail;

import java.util.List;

public interface AgentRepository extends BaseRepository<Agent> {
    Agent findByUserId(long agentUserId);

    List<Agent> findByUserIds(List<Long> userIds);

    List<Agent> findByDefault();

    List<Agent> findByEnable();

    void update(Agent agent);

    List<UserDetail> findUserDetail();
}

package com.cooba.service;

import com.cooba.entity.Agent;

import java.util.Optional;

public interface AgentService {

    long create(Agent agent);

    Optional<Agent> search(long userId);

    void disable(Agent agent);
}

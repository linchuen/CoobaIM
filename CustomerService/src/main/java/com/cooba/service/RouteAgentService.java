package com.cooba.service;

import com.cooba.entity.Agent;
import com.cooba.entity.User;

import java.util.List;

public interface RouteAgentService {
    Agent findSuitableAgent(User customer);

    Agent findSuitableAgent(List<Agent> agents, User customer);

}

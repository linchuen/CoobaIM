package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.constatnt.CsErrorEnum;
import com.cooba.entity.Agent;
import com.cooba.entity.User;
import com.cooba.exception.BaseException;
import com.cooba.repository.AgentRepository;
import com.cooba.service.RouteAgentService;
import com.cooba.service.RouteRule;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@BehaviorLayer
public class RouteAgentServiceImpl implements RouteAgentService {
    private final List<RouteRule> routeRules;
    private final AgentRepository agentRepository;

    public RouteAgentServiceImpl(List<RouteRule> routeRules, AgentRepository agentRepository) {
        routeRules.sort(Comparator.comparingInt(RouteRule::getOrder));
        this.routeRules = routeRules;
        this.agentRepository = agentRepository;
    }

    @Override
    public Agent findSuitableAgent(User customer) {
        List<Agent> agents = agentRepository.findByEnable();
        return findSuitableAgent(agents, customer);
    }

    @Override
    public Agent findSuitableAgent(List<Agent> agents, User customer) {
        List<Agent> filteredAgents = agents;
        for (RouteRule rule : this.routeRules) {
            if (!rule.isOpen()) continue;

            filteredAgents = rule.filterAgent(filteredAgents, customer);
        }
        if (!filteredAgents.isEmpty()) return filteredAgents.get(0);

        List<Agent> defaultAgents = agentRepository.findByDefault();
        if (defaultAgents.isEmpty()) {
            throw new BaseException(CsErrorEnum.AGENT_NOT_EXIST);
        }

        Collections.shuffle(defaultAgents);
        return defaultAgents.get(0);
    }
}

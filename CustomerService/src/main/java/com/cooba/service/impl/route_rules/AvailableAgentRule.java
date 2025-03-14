package com.cooba.service.impl.route_rules;


import com.cooba.entity.Agent;
import com.cooba.entity.User;
import com.cooba.service.RouteRule;
import com.cooba.util.ConnectionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AvailableAgentRule implements RouteRule {
    private final ConnectionManager connectionManager;

    @Override
    public List<Agent> filterAgent(List<Agent> agents, User customer) {
        if (agents.size() <= 1) return agents;

        return agents.stream()
                .filter(agent -> connectionManager.isUserOnline(String.valueOf(agent.getUserId())))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}


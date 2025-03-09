package com.cooba.service.impl.route_rules;

import com.cooba.entity.Agent;
import com.cooba.entity.User;
import com.cooba.service.RouteRule;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class RandomAgentRule implements RouteRule {
    private final Random random = new Random();

    @Override
    public List<Agent> filterAgent(List<Agent> agents, User customer) {
        if (agents.size() <= 1) return agents;

        return List.of(agents.get(random.nextInt(agents.size())));
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public int getOrder() {
        return 4;
    }
}


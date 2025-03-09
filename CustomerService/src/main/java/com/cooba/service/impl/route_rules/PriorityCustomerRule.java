package com.cooba.service.impl.route_rules;

import com.cooba.entity.Agent;
import com.cooba.entity.AgentCustomer;
import com.cooba.entity.User;
import com.cooba.repository.AgentCustomerRepository;
import com.cooba.service.RouteRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PriorityCustomerRule implements RouteRule {
    private final AgentCustomerRepository agentCustomerRepository;

    @Override
    public List<Agent> filterAgent(List<Agent> agents, User customer) {
        if (agents.size() <= 1) return agents;

        Set<Long> agentUserIdSet = agentCustomerRepository.findByCustomerId(customer.getId())
                .stream()
                .map(AgentCustomer::getAgentUserId)
                .collect(Collectors.toSet());

        List<Agent> vipAgents = agents.stream()
                .filter(agent -> agentUserIdSet.contains(agent.getUserId()))
                .toList();

        return vipAgents.isEmpty() ? agents : vipAgents;
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public int getOrder() {
        return 2;
    }
}

package com.cooba.service.impl.route_rules;

import com.cooba.entity.Agent;
import com.cooba.entity.User;
import com.cooba.repository.TicketRepository;
import com.cooba.service.RouteRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LeastBusyAgentRule implements RouteRule {
    private final TicketRepository ticketRepository;

    @Override
    public List<Agent> filterAgent(List<Agent> agents, User customer) {
        if (agents.size() <= 1) return agents;

        Map<Agent, Integer> agentTicketCountMap = agents.stream()
                .collect(Collectors.toMap(
                        agent -> agent,
                        agent -> ticketRepository.countOpenTicketByByAgent(agent.getUserId())
                ));
        Optional<Agent> agent = agentTicketCountMap.entrySet()
                .stream()
                .min(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey);
        return agent.map(List::of).orElse(Collections.emptyList());
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public int getOrder() {
        return 3;
    }
}

package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.dto.AgentInfo;
import com.cooba.entity.Agent;
import com.cooba.entity.AgentCustomer;
import com.cooba.entity.User;
import com.cooba.repository.AgentCustomerRepository;
import com.cooba.repository.AgentRepository;
import com.cooba.repository.UserRepository;
import com.cooba.service.AgentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {
    private final AgentRepository agentRepository;
    private final AgentCustomerRepository agentCustomerRepository;
    private final UserRepository userRepository;

    @Override
    public long create(Agent agent) {
        agentRepository.insert(agent);
        return agent.getId();
    }

    @Override
    public void update(Agent agent) {
        agentRepository.update(agent);
    }

    @Override
    public Agent search(long agentUserId) {
        return agentRepository.findByUserId(agentUserId);
    }

    @Override
    public List<AgentInfo> search(List<Long> agentIds) {
        List<Agent> agents = agentRepository.selectByIds(agentIds);
        List<Long> userIds = agents.stream().map(Agent::getUserId).toList();
        Map<Long, String> userMap = userRepository.selectByIds(userIds).stream().collect(Collectors.toMap(User::getId, User::getName));
        return agents.stream()
                .map(agent -> new AgentInfo(agent, userMap.get(agent.getUserId())))
                .collect(Collectors.toList());
    }

    @Override
    public void disable(Agent agent) {
        agent.setDisable(true);
        agentRepository.update(agent);
    }

    @Override
    public List<AgentCustomer> searchCustomer(long agentUserId) {
        return agentCustomerRepository.findByAgentId(agentUserId);
    }

    @Override
    public void bindCustomer(List<AgentCustomer> agentCustomers) {
        agentCustomerRepository.insert(agentCustomers);
    }

    @Override
    public void unbindCustomer(long agentUserId, List<Long> customerUserIds) {
        agentCustomerRepository.deleteByCustomerUserIds(agentUserId, customerUserIds);
    }
}


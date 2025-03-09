package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.entity.Agent;
import com.cooba.entity.AgentCustomer;
import com.cooba.repository.AgentCustomerRepository;
import com.cooba.repository.AgentRepository;
import com.cooba.service.AgentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {
    private final AgentRepository agentRepository;
    private final AgentCustomerRepository agentCustomerRepository;

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
    public List<Agent> search(List<Long> agentIds) {
        return agentRepository.selectByIds(agentIds);
    }

    @Override
    public void disable(Agent agent) {
        agent.setDisable(true);
        agentRepository.update(agent);
    }

    @Override
    public List<AgentCustomer> searchCustomer(long agentId) {
        return agentCustomerRepository.findByAgentId(agentId);
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


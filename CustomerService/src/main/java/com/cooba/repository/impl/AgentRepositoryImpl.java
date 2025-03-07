package com.cooba.repository.impl;

import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.Agent;
import com.cooba.mapper.AgentMapper;
import com.cooba.repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
public class AgentRepositoryImpl implements AgentRepository {
    private final AgentMapper agentMapper;

    @Override
    public void insert(Agent agent) {
        agentMapper.insert(agent);
    }

    @Override
    public void insert(List<Agent> t) {
        agentMapper.insert(t);
    }

    @Override
    public Agent selectById(long id) {
        return agentMapper.selectById(id);
    }

    @Override
    public List<Agent> selectByIds(List<Long> ids) {
        return agentMapper.selectByIds(ids);
    }

    @Override
    public void deleteById(long id) {
        agentMapper.deleteById(id);
    }
}

package com.cooba.repository.impl;

import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.AgentCustomer;
import com.cooba.mapper.AgentCustomerMapper;
import com.cooba.repository.AgentCustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
public class AgentCustomerRepositoryImpl implements AgentCustomerRepository {
    private final AgentCustomerMapper agentCustomerMapper;
    @Override
    public void insert(AgentCustomer agentCustomer) {
        agentCustomerMapper.insert(agentCustomer);
    }

    @Override
    public void insert(List<AgentCustomer> t) {
        agentCustomerMapper.insert(t);
    }

    @Override
    public AgentCustomer selectById(long id) {
        return agentCustomerMapper.selectById(id);
    }

    @Override
    public List<AgentCustomer> selectByIds(List<Long> ids) {
        return agentCustomerMapper.selectByIds(ids);
    }

    @Override
    public void deleteById(long id) {
        agentCustomerMapper.deleteById(id);
    }
}

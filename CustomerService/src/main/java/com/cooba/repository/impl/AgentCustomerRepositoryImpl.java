package com.cooba.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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


    @Override
    public List<AgentCustomer> findByAgentId(long agentId) {
        return agentCustomerMapper.selectList(new LambdaQueryWrapper<AgentCustomer>()
                .eq(AgentCustomer::getAgentId, agentId)
        );
    }

    @Override
    public List<AgentCustomer> findByCustomerId(long customerUserId) {
        return agentCustomerMapper.selectList(new LambdaQueryWrapper<AgentCustomer>()
                .eq(AgentCustomer::getCustomerUserId, customerUserId)
        );
    }

    @Override
    public void deleteByCustomerUserIds(long agentUserId, List<Long> customerUserIds) {
        agentCustomerMapper.delete(new LambdaQueryWrapper<AgentCustomer>()
                .eq(AgentCustomer::getAgentUserId, agentUserId)
                .in(AgentCustomer::getCustomerUserId,customerUserIds)
        );
    }
}

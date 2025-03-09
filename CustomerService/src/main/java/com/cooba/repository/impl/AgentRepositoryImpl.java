package com.cooba.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cooba.annotation.DataManipulateLayer;
import com.cooba.constant.ErrorEnum;
import com.cooba.constatnt.CsErrorEnum;
import com.cooba.entity.Agent;
import com.cooba.exception.BaseException;
import com.cooba.mapper.AgentMapper;
import com.cooba.repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Agent findByUserId(long userId) {
        Agent agent = agentMapper.selectOne(new LambdaQueryWrapper<Agent>()
                .eq(Agent::getUserId, userId));
        if (agent == null) {
            throw new BaseException(CsErrorEnum.AGENT_NOT_EXIST);
        }
        return agent;
    }

    @Override
    public void update(Agent agent) {
        int row = agentMapper.update(new LambdaUpdateWrapper<Agent>()
                .eq(Agent::getId, agent.getId())
                .set(Agent::isDisable, agent.isDisable())
                .set(Agent::getUpdatedTime, LocalDateTime.now())
        );
        if (row == 0) {
            throw new BaseException(ErrorEnum.INVALID_REQUEST);
        }
    }
}

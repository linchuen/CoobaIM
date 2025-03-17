package com.cooba.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cooba.annotation.DataManipulateLayer;
import com.cooba.constant.ErrorEnum;
import com.cooba.constatnt.CsErrorEnum;
import com.cooba.entity.Agent;
import com.cooba.entity.UserDetail;
import com.cooba.exception.BaseException;
import com.cooba.mapper.AgentMapper;
import com.cooba.repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
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
        return agentMapper.selectList(new LambdaQueryWrapper<Agent>()
                .in(!ids.isEmpty(), Agent::getId, ids));
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
    public List<Agent> findByUserIds(List<Long> userIds) {
        return agentMapper.selectList(new LambdaQueryWrapper<Agent>()
                .in(Agent::getUserId, userIds));
    }

    @Override
    public List<Agent> findByDefault() {
        return agentMapper.selectList(new LambdaQueryWrapper<Agent>()
                .eq(Agent::getIsDisable, false)
                .eq(Agent::getIsDefault, true));
    }

    @Override
    public void update(Agent agent) {
        int row = agentMapper.update(new LambdaUpdateWrapper<Agent>()
                .eq(agent.getId() != null, Agent::getId, agent.getId())
                .eq(agent.getUserId() != null, Agent::getUserId, agent.getUserId())
                .set(Agent::getIsDisable, agent.getIsDisable())
                .set(Agent::getUpdatedTime, LocalDateTime.now())
        );
        if (row == 0) {
            throw new BaseException(ErrorEnum.BUSINESS_ERROR);
        }
    }

    @Override
    public List<UserDetail> findUserDetail() {
        return agentMapper.findUserDetail();
    }
}

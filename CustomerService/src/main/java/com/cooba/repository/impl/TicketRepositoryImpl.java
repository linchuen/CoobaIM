package com.cooba.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.Ticket;
import com.cooba.mapper.TicketMapper;
import com.cooba.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketRepository {
    private final TicketMapper ticketMapper;

    @Override
    public void insert(Ticket ticket) {
        ticketMapper.insert(ticket);
    }

    @Override
    public void insert(List<Ticket> t) {
        ticketMapper.insert(t);
    }

    @Override
    public Ticket selectById(long id) {
        return ticketMapper.selectById(id);
    }

    @Override
    public List<Ticket> selectByIds(List<Long> ids) {
        return ticketMapper.selectByIds(ids);
    }

    @Override
    public void deleteById(long id) {
        ticketMapper.deleteById(id);
    }

    @Override
    public Optional<Ticket> findLastTicketByCustomerId(long customerUserId) {
        Ticket ticket = ticketMapper.selectOne(new LambdaQueryWrapper<Ticket>()
                .eq(Ticket::getCustomerUserId, customerUserId)
                .orderByDesc(Ticket::getId)
                .last("limit 1")
        );
        return Optional.ofNullable(ticket);
    }

    @Override
    public List<Ticket> findTicketsByAgentAndCustomer(long agentUserId, long customerUserId) {
        return ticketMapper.selectList(new LambdaQueryWrapper<Ticket>()
                .eq(Ticket::getAgentUserId, agentUserId)
                .eq(Ticket::getCustomerUserId, customerUserId)
        );
    }

    @Override
    public int countOpenTicketByByAgent(long agentUserId) {
        return Math.toIntExact(ticketMapper.selectCount(new LambdaQueryWrapper<Ticket>()
                .eq(Ticket::getAgentUserId, agentUserId)
                .eq(Ticket::getIsOpen, true)
        ));
    }

    @Override
    public List<Ticket> findTicketByByAgent(long agentUserId) {
        return ticketMapper.selectList(new LambdaQueryWrapper<Ticket>()
                .eq(Ticket::getAgentUserId, agentUserId));
    }

    @Override
    public List<Ticket> findTicketByByAgent(long agentUserId, Integer limit) {
        return ticketMapper.selectList(new LambdaQueryWrapper<Ticket>()
                .eq(Ticket::getAgentUserId, agentUserId)
                .last("limit " + limit));
    }
}

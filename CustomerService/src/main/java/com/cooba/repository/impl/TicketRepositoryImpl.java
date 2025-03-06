package com.cooba.repository.impl;

import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.Ticket;
import com.cooba.mapper.TicketMapper;
import com.cooba.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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
}

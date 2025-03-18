package com.cooba.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.Agent;
import com.cooba.entity.Guest;
import com.cooba.mapper.GuestMapper;
import com.cooba.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
public class GuestRepositoryImpl implements GuestRepository {
    private final GuestMapper guestMapper;

    @Override
    public void insert(Guest guest) {
        guestMapper.insert(guest);
    }

    @Override
    public void insert(List<Guest> t) {
        guestMapper.insert(t);
    }

    @Override
    public Guest selectById(long id) {
        return guestMapper.selectById(id);
    }

    @Override
    public List<Guest> selectByIds(List<Long> ids) {
        return guestMapper.selectByIds(ids);
    }

    @Override
    public List<Guest> selectByIdsElseAll(List<Long> ids) {
        return guestMapper.selectList(new LambdaQueryWrapper<Guest>()
                .in(!ids.isEmpty(),Guest::getId, ids));
    }

    @Override
    public void deleteById(long id) {
        guestMapper.deleteById(id);
    }
}

package com.cooba.repository.impl;

import com.cooba.entity.Guest;
import com.cooba.repository.GuestRepository;

import java.util.List;

public class GuestRepositoryImpl implements GuestRepository {
    @Override
    public void insert(Guest guest) {

    }

    @Override
    public void insert(List<Guest> t) {

    }

    @Override
    public Guest selectById(long id) {
        return null;
    }

    @Override
    public List<Guest> selectByIds(List<Long> ids) {
        return List.of();
    }

    @Override
    public void deleteById(long id) {

    }
}

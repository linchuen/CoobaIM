package com.cooba.repository;

import jakarta.el.MethodNotFoundException;

import java.util.List;

public interface BaseRepository<T> {

    void insert(T t);

    void insert(List<T> t);

    T selectById(long id);

    default List<T> selectByIdsElseAll(List<Long> ids) {
        throw new MethodNotFoundException();
    }

    List<T> selectByIds(List<Long> ids);

    void deleteById(long id);
}

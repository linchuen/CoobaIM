package com.cooba.repository;

import java.util.List;

public interface BaseRepository<T> {

    void insert(T t);

    void insert(List<T> t);

    T selectById(long id);

    List<T> selectByIds(List<Long> ids);

    void deleteById(long id);
}

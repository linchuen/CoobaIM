package com.cooba.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.User;
import com.cooba.mapper.UserMapper;
import com.cooba.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;


@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserMapper userMapper;

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public void insert(List<User> users) {
        userMapper.insert(users);
    }

    @Override
    public User selectById(long id) {
        return userMapper.selectById(id);
    }

    @Override
    public void deleteById(long id) {
        userMapper.deleteById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getEmail, email));
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByName(String name) {
        User user =  userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getName, name));
        return Optional.ofNullable(user);
    }
}


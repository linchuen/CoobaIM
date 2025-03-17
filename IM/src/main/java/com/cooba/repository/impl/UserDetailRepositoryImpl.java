package com.cooba.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.UserDetail;
import com.cooba.mapper.UserDetailMapper;
import com.cooba.repository.UserDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
public class UserDetailRepositoryImpl implements UserDetailRepository {
    private final UserDetailMapper userDetailMapper;

    @Override
    public void insert(UserDetail userDetail) {
        userDetailMapper.insert(userDetail);
    }

    @Override
    public void insert(List<UserDetail> t) {
        userDetailMapper.insert(t);
    }

    @Override
    public UserDetail selectById(long id) {
        return userDetailMapper.selectById(id);
    }

    @Override
    public List<UserDetail> selectByIds(List<Long> ids) {
        return userDetailMapper.selectByIds(ids);
    }

    @Override
    public void deleteById(long id) {
        userDetailMapper.deleteById(id);
    }

    @Override
    public List<UserDetail> findByUserId(List<Long> userIds) {
        return userDetailMapper.selectList(new LambdaQueryWrapper<UserDetail>()
                .in(!userIds.isEmpty(), UserDetail::getUserId, userIds));
    }
}

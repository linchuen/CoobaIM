package com.cooba.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.Notification;
import com.cooba.mapper.NotificationMapper;
import com.cooba.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {
    private final NotificationMapper notificationMapper;

    @Override
    public void insert(Notification notification) {
        notificationMapper.insert(notification);
    }

    @Override
    public void insert(List<Notification> notifications) {
        notificationMapper.insert(notifications);
    }

    @Override
    public Notification selectById(long id) {
        return notificationMapper.selectById(id);
    }

    @Override
    public List<Notification> selectByIds(List<Long> ids) {
        return notificationMapper.selectByIds(ids);
    }

    @Override
    public void deleteById(long id) {
        notificationMapper.deleteById(id);
    }

    @Override
    public List<Notification> findAll() {
        return notificationMapper.selectList(new LambdaQueryWrapper<>());
    }
}
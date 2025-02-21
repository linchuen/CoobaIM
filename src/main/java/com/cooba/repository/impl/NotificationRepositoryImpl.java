package com.cooba.repository.impl;

import com.cooba.annotation.DataManipulateLayer;
import com.cooba.entity.Notification;
import com.cooba.mapper.NotificationMapper;
import com.cooba.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@DataManipulateLayer
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {
    private final NotificationMapper notificationMapper;

    @Override
    public void insert(Notification notification) {
        notificationMapper.insert(notification);
    }
}
package com.cooba.repository;

import com.cooba.entity.Notification;

import java.util.List;

public interface NotificationRepository extends BaseRepository<Notification> {

    List<Notification> findAll();
}

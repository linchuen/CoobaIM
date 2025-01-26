package com.cooba.service;

import com.cooba.entity.RoomUser;
import com.cooba.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.List;

public interface UserService extends UserDetailsService {
    void register(User user);

    void connectRoom(RoomUser roomUser);

    void disconnectRoom(RoomUser roomUser);

    User getInfo(long userId);

    List<RoomUser> getAllRooms(long userId);
}

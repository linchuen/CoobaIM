package com.cooba.service;

import com.cooba.entity.RoomUser;
import com.cooba.entity.User;
import com.cooba.entity.UserDetail;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    long register(User user);

    void connectRoom(RoomUser roomUser);

    void disconnectRoom(RoomUser roomUser);

    void verifyPassword(User user, String password);

    User getInfo(long userId);

    User getInfo(String email, String partner);

    User getInfoByName(String name,String partner);

    UserDetail getDetail(long userId);

    List<User> getInfoList(List<Long> userIds);

    List<RoomUser> getAllRooms(long userId);

    List<UserDetail> getDetailList(List<Long> userIds);
}

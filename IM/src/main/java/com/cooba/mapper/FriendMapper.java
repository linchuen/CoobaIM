package com.cooba.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cooba.dto.FriendInfo;
import com.cooba.entity.Friend;

import java.util.List;

public interface FriendMapper extends BaseMapper<Friend> {

    List<FriendInfo> findWithAvatar(long userId);
}

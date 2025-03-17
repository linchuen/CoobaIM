package com.cooba.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cooba.entity.Agent;
import com.cooba.entity.UserDetail;

import java.util.List;

public interface AgentMapper extends BaseMapper<Agent> {
    List<UserDetail> findUserDetail();
}

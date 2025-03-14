package com.cooba.service;

import com.cooba.entity.OfficialChannel;

import java.util.List;

public interface OfficialChannelService {
    Long create(OfficialChannel officialChannel);

    void delete(long channelId);

    List<OfficialChannel> searchAll();
}

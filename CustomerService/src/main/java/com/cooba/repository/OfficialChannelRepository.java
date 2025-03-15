package com.cooba.repository;

import com.cooba.entity.OfficialChannel;

public interface OfficialChannelRepository extends BaseRepository<OfficialChannel>{

    void update(OfficialChannel channel);
}

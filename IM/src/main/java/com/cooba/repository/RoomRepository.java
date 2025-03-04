package com.cooba.repository;

import com.cooba.entity.Room;

import java.util.List;

public interface RoomRepository extends BaseRepository<Room> {

    List<Room> selectByIds(List<Long> ids);
}

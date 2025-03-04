package com.cooba.dto.response;

import com.cooba.entity.Room;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RoomSearchResponse {
    private List<Room> rooms;
}

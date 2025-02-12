package com.cooba.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RoomSearchRequest {
    private List<Long> roomIds;
    private String name;
}

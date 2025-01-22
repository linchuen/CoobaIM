package com.cooba.dto.request;

import lombok.Data;

@Data
public class RoomUserRequest {

    private Long id;
    private Long roomId;
    private Long userId;
}

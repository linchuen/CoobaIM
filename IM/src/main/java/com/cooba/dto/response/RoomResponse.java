package com.cooba.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoomResponse {
    private Long roomId;
    private Long userId;
}

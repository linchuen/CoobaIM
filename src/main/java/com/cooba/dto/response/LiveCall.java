package com.cooba.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveCall {
    private long roomId;
    private String roomName;
    private String passcode;
    private String token;
}

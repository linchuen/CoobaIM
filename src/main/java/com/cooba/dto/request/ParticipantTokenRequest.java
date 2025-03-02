package com.cooba.dto.request;

import lombok.Data;

@Data
public class ParticipantTokenRequest {
    private String name;
    private String id;
    private String roomName;
    private String passcode;
}

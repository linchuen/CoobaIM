package com.cooba.dto.request;

import lombok.Data;

@Data
public class MessageRequest {
    private String msg;
    private String uid;
    private String groupId;
}

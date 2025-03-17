package com.cooba.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ChatLoadLastAndUnReadRequest {
    private List<Long> roomIds;
}

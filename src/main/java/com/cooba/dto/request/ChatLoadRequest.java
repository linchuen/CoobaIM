package com.cooba.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ChatLoadRequest {
    private List<Long> roomIds;
}

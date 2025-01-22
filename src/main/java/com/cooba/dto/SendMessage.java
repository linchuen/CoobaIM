package com.cooba.dto;

import com.cooba.entity.User;
import lombok.Data;

@Data
public class SendMessage {
    private long roomId;
    private User user;
    private String msg;
}

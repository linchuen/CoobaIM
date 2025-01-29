package com.cooba.dto;

import com.cooba.constant.MessageTypeEnum;
import com.cooba.entity.User;
import lombok.Data;

@Data
public class SendMessage {
    private long roomId;
    private User user;
    private String message;
    private MessageTypeEnum type = MessageTypeEnum.TEXT;
}

package com.cooba.dto;

import com.cooba.constant.MessageTypeEnum;
import com.cooba.entity.User;
import lombok.Data;

@Data
public class SendMessage {
    private long roomId;
    private long userId;
    private String name;
    private String message;
    private String url;
    private MessageTypeEnum type = MessageTypeEnum.TEXT;
}

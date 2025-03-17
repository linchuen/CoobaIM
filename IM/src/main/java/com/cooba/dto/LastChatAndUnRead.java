package com.cooba.dto;

import com.cooba.entity.Chat;
import lombok.Data;

@Data
public class LastChatAndUnRead {
    private long roomId;
    private Chat chat;
    private long unread;
}

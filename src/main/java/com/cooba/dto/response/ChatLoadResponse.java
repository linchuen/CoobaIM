package com.cooba.dto.response;

import com.cooba.entity.Chat;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChatLoadResponse {
    private List<Chat> chats;
}

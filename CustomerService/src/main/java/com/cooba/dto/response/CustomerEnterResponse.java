package com.cooba.dto.response;

import com.cooba.entity.Chat;
import com.cooba.entity.Ticket;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CustomerEnterResponse {
    private Ticket ticket;
    private List<Chat> chats;
}

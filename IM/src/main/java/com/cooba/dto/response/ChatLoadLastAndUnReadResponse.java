package com.cooba.dto.response;

import com.cooba.dto.LastChatAndUnRead;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChatLoadLastAndUnReadResponse {
    private List<LastChatAndUnRead> chatAndUnReads;
}

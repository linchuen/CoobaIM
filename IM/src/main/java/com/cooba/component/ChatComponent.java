package com.cooba.component;

import com.cooba.dto.request.ChatLoadLastAndUnReadRequest;
import com.cooba.dto.request.ChatLoadRequest;
import com.cooba.dto.request.SpeakRequest;
import com.cooba.dto.response.ChatLoadLastAndUnReadResponse;
import com.cooba.dto.response.ChatLoadResponse;

public interface ChatComponent {
    void speakToUser(SpeakRequest request);

    void speakToRoom(SpeakRequest request);

    void speakToAll(SpeakRequest request);

    ChatLoadResponse load(ChatLoadRequest request);

    ChatLoadLastAndUnReadResponse loadLastChatAndUnreadCount(ChatLoadLastAndUnReadRequest request);
}

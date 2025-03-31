package com.cooba.component;

import com.cooba.dto.request.*;
import com.cooba.dto.response.ChatLoadLastAndUnReadResponse;
import com.cooba.dto.response.ChatLoadResponse;

public interface ChatComponent {
    void speakToUser(SpeakRequest request);

    void speakToRoom(SpeakRequest request);

    void speakToAll(SpeakRequest request);

    ChatLoadResponse load(ChatLoadRequest request);

    ChatLoadLastAndUnReadResponse loadLastChatAndUnreadCount(ChatLoadLastAndUnReadRequest request);

    void setIsRead(ChatIsReadRequest request);

    ChatLoadResponse loadByDate(ChatLoadDateRequest request);

    void searchWord(ChatSearchRequest request);

}

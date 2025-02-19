package com.cooba.core.spring;

import com.cooba.component.ChatComponent;
import com.cooba.dto.request.SpeakRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketMsgController {
    private final ChatComponent chatComponent;

    @MessageMapping("/sendToUser")
    public void sendToUser(@Payload SpeakRequest message) {
        chatComponent.speakToUser(message);
    }

    @MessageMapping("/sendToRoom")
    public void sendToRoom(@Payload SpeakRequest message) {
        chatComponent.speakToRoom(message);
    }

    @MessageMapping("/sendToAll")
    @SendTo("/topic/broadcast")
    public void sendToAll(@Payload SpeakRequest message) {
        chatComponent.speakToAll(message);
    }
}
package com.cooba.core.spring;

import com.cooba.component.ChatComponent;
import com.cooba.constant.ErrorEnum;
import com.cooba.constant.EventEnum;
import com.cooba.core.SocketConnection;
import com.cooba.dto.request.SpeakRequest;
import com.cooba.exception.ChatMessageException;
import com.cooba.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebSocketMsgController {
    private final ChatComponent chatComponent;
    private final SocketConnection socketConnection;

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

    @MessageExceptionHandler(ChatMessageException.class)
    public void handleChatMessageException(ChatMessageException e, Principal principal) {
        log.error("message error", e);
        socketConnection.sendUserEvent(principal.getName(), EventEnum.ERROR, JsonUtil.toJson(e.getChat()));
    }

    @MessageExceptionHandler(Exception.class)
    public void handleException(Exception e, Principal principal) {
        log.error("WebSocketMsg error", e);
        socketConnection.sendUserEvent(principal.getName(), EventEnum.ERROR, ErrorEnum.UNKNOWN_ERROR.getMessage());
    }
}
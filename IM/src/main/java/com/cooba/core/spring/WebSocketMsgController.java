package com.cooba.core.spring;

import com.cooba.component.ChatComponent;
import com.cooba.constant.ErrorEnum;
import com.cooba.constant.EventEnum;
import com.cooba.core.SocketConnection;
import com.cooba.dto.request.SpeakRequest;
import com.cooba.dto.response.ResultResponse;
import com.cooba.exception.BaseException;
import com.cooba.exception.IMError;
import com.cooba.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.UUID;

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

    @MessageExceptionHandler(BaseException.class)
    public void handleException(BaseException ex, Principal principal) {
        String uuid = UUID.randomUUID().toString();
        IMError imError = ex.getError() == null ? ErrorEnum.BUSINESS_ERROR : ex.getError();
        log.error("WebSocket BaseException {}", uuid, ex);
        ResultResponse<Object> result = ResultResponse.builder()
                .traceId(uuid)
                .errorMessage(imError.getMessage())
                .code(imError.getCode())
                .logMessage(ex.getMessage())
                .build();
        socketConnection.sendUserEvent(principal.getName(), EventEnum.ERROR, JsonUtil.toJson(result));
    }

    @MessageExceptionHandler(Exception.class)
    public void handleException(Exception ex, Principal principal) {
        String uuid = UUID.randomUUID().toString();
        log.error("WebSocket Exception", ex);
        ResultResponse<Object> result = ResultResponse.builder()
                .traceId(uuid)
                .errorMessage(ErrorEnum.UNKNOWN_ERROR.getMessage())
                .code(ErrorEnum.UNKNOWN_ERROR.getCode())
                .logMessage(ex.getMessage())
                .build();
        socketConnection.sendUserEvent(principal.getName(), EventEnum.ERROR, JsonUtil.toJson(result));
    }
}
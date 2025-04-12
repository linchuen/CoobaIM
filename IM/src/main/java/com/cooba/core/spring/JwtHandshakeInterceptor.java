package com.cooba.core.spring;

import com.cooba.constant.ErrorEnum;
import com.cooba.exception.BaseException;
import com.cooba.util.ConnectionManager;
import com.cooba.util.JwtHeaderValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtHandshakeInterceptor implements ChannelInterceptor {
    private final JwtHeaderValidator jwtHeaderValidator;
    private final ConnectionManager connectionManager;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();
        String user = Objects.requireNonNull(accessor.getUser()).getName();
        String destination = accessor.getDestination();

        if (accessor.isHeartbeat()) return message;
        switch (accessor.getCommand()) {
            case CONNECT -> {
                log.info("SessionId: {} userId: {} CONNECT", sessionId, user);
                String authToken = accessor.getFirstNativeHeader("Authorization");

                Long userId = jwtHeaderValidator.validHeader(authToken);
                if (!userId.toString().equals(user)) {
                    throw new BaseException(ErrorEnum.INVALID_AUTHORIZATION);
                }
                connectionManager.addUser(user, sessionId);
            }
            case DISCONNECT -> {
                log.info("SessionId: {} userId: {} DISCONNECT", sessionId, user);
                connectionManager.removeUser(user);
            }
            default -> {
                log.info("Command: {} userId: {} destination: {}", accessor.getCommand(), user, destination);
            }
        }
        return message;
    }
}

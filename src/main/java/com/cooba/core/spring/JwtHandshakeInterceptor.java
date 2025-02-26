package com.cooba.core.spring;

import com.cooba.constant.ErrorEnum;
import com.cooba.exception.BaseException;
import com.cooba.util.JwtHeaderValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtHandshakeInterceptor implements ChannelInterceptor {
    private final JwtHeaderValidator jwtHeaderValidator;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authToken = accessor.getFirstNativeHeader("Authorization");

            Long userId = jwtHeaderValidator.validHeader(authToken);
            if (!userId.toString().equals(accessor.getUser().getName())) {
                throw new BaseException(ErrorEnum.INVALID_AUTHORIZATION);
            }
        }
        return message;
    }
}

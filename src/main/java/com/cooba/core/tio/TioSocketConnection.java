package com.cooba.core.tio;

import com.cooba.core.SocketConnection;
import lombok.RequiredArgsConstructor;
import org.tio.core.Tio;
import org.tio.websocket.common.WsResponse;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class TioSocketConnection implements SocketConnection {
    private final TioWebSocketServerBootstrap bootstrap;

    @Override
    public void bindGroup(String userid, String group) {
        Tio.bindGroup(bootstrap.getServerTioConfig(), userid, group);
    }

    @Override
    public void unbindGroup(String userid, String group) {
        Tio.unbindGroup(bootstrap.getServerTioConfig(), userid, group);
    }

    @Override
    public void sendToUser(String userid, String message) {
        Tio.sendToUser(bootstrap.getServerTioConfig(), userid, WsResponse.fromText(message, StandardCharsets.UTF_8.name()));
    }

    @Override
    public void sendToGroup(String group, String message) {
        Tio.sendToGroup(bootstrap.getServerTioConfig(), group, WsResponse.fromText(message, StandardCharsets.UTF_8.name()));
    }

    @Override
    public void sendToAll(String message) {
        Tio.sendToAll(bootstrap.getServerTioConfig(), WsResponse.fromText(message, StandardCharsets.UTF_8.name()));
    }
}

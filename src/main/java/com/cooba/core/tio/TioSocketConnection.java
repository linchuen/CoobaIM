package com.cooba.core.tio;

import com.cooba.core.SocketConnection;
import lombok.RequiredArgsConstructor;
import org.tio.core.Tio;
import org.tio.server.ServerTioConfig;
import org.tio.websocket.common.WsResponse;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class TioSocketConnection implements SocketConnection {
    private final ServerTioConfig serverTioConfig;

    @Override
    public void bindGroup(String userid, String group) {
        Tio.bindGroup(serverTioConfig, userid, group);
    }

    @Override
    public void unbindGroup(String userid, String group) {
        Tio.unbindGroup(serverTioConfig, userid, group);
    }

    @Override
    public void sendToUser(String userid, String message) {
        Tio.sendToUser(serverTioConfig, userid, WsResponse.fromText(message, StandardCharsets.UTF_8.name()));
    }

    @Override
    public void sendToGroup(String group, String message) {
        Tio.sendToGroup(serverTioConfig, group, WsResponse.fromText(message, StandardCharsets.UTF_8.name()));
    }

    @Override
    public void sendToAll(String message) {
        Tio.sendToAll(serverTioConfig, WsResponse.fromText(message, StandardCharsets.UTF_8.name()));
    }
}

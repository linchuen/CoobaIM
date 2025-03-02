package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.constant.Livekit;
import com.cooba.service.LiveKitService;
import io.livekit.server.AccessToken;
import io.livekit.server.RoomJoin;
import io.livekit.server.RoomName;
import io.livekit.server.RoomServiceClient;
import livekit.LivekitModels;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class LiveKitServiceImpl implements LiveKitService {
    private final RoomServiceClient client;
    private final Livekit livekit;

    public String createRoom(int maxParticipants) throws IOException {
        String roomName = UUID.randomUUID().toString();
        Call<LivekitModels.Room> call = client.createRoom(roomName, 30, maxParticipants);
        call.execute();

        return roomName;
    }

    public String createAccessToken(String name, String identity, String roomName, String passcode) {
        AccessToken token = new AccessToken(livekit.getKey(), livekit.getSecret());
        token.setName(name);
        token.setIdentity(identity);
        token.setMetadata(passcode);
        token.addGrants(new RoomJoin(true), new RoomName(roomName));

        return token.toJwt();
    }
}

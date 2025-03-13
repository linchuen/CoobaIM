package com.cooba.service.impl;

import io.livekit.server.AccessToken;
import io.livekit.server.RoomJoin;
import io.livekit.server.RoomName;
import io.livekit.server.RoomServiceClient;
import livekit.LivekitModels;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
class LiveKitServiceImplTest {

    @Test
    @Disabled
    void createRoom() throws IOException {
        RoomServiceClient client = RoomServiceClient.createClient(
                "http://127.0.0.1:7880",
                "livekit_api_key",
                "this_is_livekit_secret_key_for_test");
        Call<LivekitModels.Room> call = client.createRoom("test",  5);
        Response<LivekitModels.Room> response = call.execute();
        System.out.println(response.body());
    }

    @Test
    @Disabled
    void createAccessToken() {
        AccessToken token = new AccessToken("livekit_api_key", "this_is_livekit_secret_key_for_test");
        token.setName("test");
        token.setIdentity("1");
        token.setMetadata("");
        token.addGrants(new RoomJoin(true), new RoomName("test"));

        String jwt = token.toJwt();
        System.out.println("jwt = " + jwt);
    }
}
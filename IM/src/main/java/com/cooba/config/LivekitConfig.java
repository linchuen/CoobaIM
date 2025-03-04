package com.cooba.config;

import com.cooba.constant.Livekit;
import io.livekit.server.RoomServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LivekitConfig {
    @Bean
    public RoomServiceClient roomServiceClient(Livekit livekit) {
        return RoomServiceClient.createClient(
                livekit.getUrl(),
                livekit.getKey(),
                livekit.getSecret());
    }
}

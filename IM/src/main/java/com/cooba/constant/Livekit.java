package com.cooba.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "livekit")
public class Livekit {
    private String url;
    private String key;
    private String secret;
}

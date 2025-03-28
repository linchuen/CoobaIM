package com.cooba.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jwt.secret")
public class JwtSecret {
    private String defaultValue = "test";
    private long ttlSecond = 24 * 60 * 60;
    private long ttlDay = 1L;
}

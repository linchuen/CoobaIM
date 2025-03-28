package com.cooba.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "webhook")
public class Webhook {
    private String domain;
    private boolean enabled;
    private String accessKey;
    private Map<WebhookEnum, String> event;
}

package com.cooba.constant;

import lombok.Data;

import java.util.Map;

@Data
public class Webhook {
    private String domain;
    private boolean enabled;
    private String accessKey;
    private Map<WebhookEnum, String> event;
}

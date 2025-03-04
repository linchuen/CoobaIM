package com.cooba.dto;

import lombok.Data;

@Data
public class WebhookPayload {
    private String method;
    private String arguments;
    private Object result;
    private String type;
}

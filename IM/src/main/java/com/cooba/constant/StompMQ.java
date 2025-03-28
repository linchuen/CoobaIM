package com.cooba.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "stomp.mq")
public class StompMQ {
    private String relayHost;
    private Integer relayPort;
    private String login;
    private String passcode;
    private Boolean enable = false;
}

package com.cooba.constant;

import lombok.Data;

@Data
public class StompMQ {
    private String relayHost;
    private Integer relayPost;
    private String login;
    private String passcode;
}

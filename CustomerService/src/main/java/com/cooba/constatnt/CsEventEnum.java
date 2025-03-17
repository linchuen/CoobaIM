package com.cooba.constatnt;

import com.cooba.constant.IMEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CsEventEnum implements IMEvent {
    CHANNEL_ADD("channel_add"),
    CHANNEL_UPDATE("channel_update"),
    CHANNEL_DELETE("channel_delete"),
    TICKET_ADD("ticket_add"),
    ;

    private final String type;
}

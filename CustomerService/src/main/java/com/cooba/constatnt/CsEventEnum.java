package com.cooba.constatnt;

import com.cooba.constant.IMEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CsEventEnum implements IMEvent {
    CHANNEL_UPDATE("channel_update"),
    CHANNEL_DELETE("channel_delete"),
    ;

    private final String type;
}

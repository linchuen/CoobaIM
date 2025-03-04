package com.cooba.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WebhookEnum {
    ADD_FRIEND("addFriend");

    private final String name;
}

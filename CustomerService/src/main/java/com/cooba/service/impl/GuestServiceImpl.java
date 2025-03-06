package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.service.GuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {
    @Override
    public void create(long userId) {

    }

    @Override
    public long getRandomGuest() {
        return 0;
    }
}

package com.cooba.service.impl;

import com.cooba.annotation.BehaviorLayer;
import com.cooba.entity.Guest;
import com.cooba.repository.GuestRepository;
import com.cooba.service.GuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Slf4j
@BehaviorLayer
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {
    private final GuestRepository guestRepository;

    @Override
    public void create(long userId) {
        Guest guest = new Guest();
        guest.setUserId(userId);

        guestRepository.insert(guest);
    }

    @Override
    public long getRandomGuest() {
        List<Guest> guests = guestRepository.selectByIds(Collections.emptyList());
        if (guests.isEmpty()) {
            log.warn("No guests available");
            throw new NoSuchElementException("No guests found");
        }

        Guest randomGuest = guests.get(new Random().nextInt(guests.size()));
        return randomGuest.getId();
    }
}

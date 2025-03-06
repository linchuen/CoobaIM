package com.cooba.service;

public interface GuestService {

    void create(long userId);

    long getRandomGuest();
}

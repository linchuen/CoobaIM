package com.cooba.service;

import java.io.IOException;

public interface LiveKitService {

    String createRoom(int maxParticipants) throws IOException;

    void deleteRoom(String roomName) throws IOException;

    String createAccessToken(String name, String identity, String roomName, String passcode);
}

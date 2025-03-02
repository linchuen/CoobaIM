package com.cooba.service;

import java.io.IOException;

public interface LiveKitService {

    String createRoom(int maxParticipants) throws IOException;

    String createAccessToken(String name,String id, String roomName, String passcode);
}

package com.cooba.component;

import com.cooba.dto.request.LiveBuildRequest;
import com.cooba.dto.request.ParticipantTokenRequest;
import com.cooba.dto.response.LiveCall;

public interface LiveComponent {

    LiveCall createRoom(LiveBuildRequest request);

    String createAccessToken(ParticipantTokenRequest request);
}

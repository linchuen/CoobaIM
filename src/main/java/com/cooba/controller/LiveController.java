package com.cooba.controller;

import com.cooba.component.LiveComponent;
import com.cooba.dto.request.LiveBuildRequest;
import com.cooba.dto.request.ParticipantTokenRequest;
import com.cooba.dto.response.LiveCall;
import com.cooba.dto.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/live")
@RequiredArgsConstructor
public class LiveController {
    private final LiveComponent liveComponent;

    @PostMapping("/build")
    @Operation(summary = "視訊建立")
    public ResultResponse<?> createRoom(@RequestBody LiveBuildRequest request) {
        LiveCall liveCall = liveComponent.createRoom(request);
        return ResultResponse.builder().data(liveCall).build();
    }

    @PostMapping("/participant")
    @Operation(summary = "參與者token建立")
    public ResultResponse<?> createAccessToken(@RequestBody ParticipantTokenRequest request) {
        String accessToken = liveComponent.createAccessToken(request);
        return ResultResponse.builder().data(accessToken).build();
    }
}

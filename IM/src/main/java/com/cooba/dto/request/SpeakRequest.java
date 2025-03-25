package com.cooba.dto.request;

import com.cooba.proto.SpeakProto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SpeakRequest {
    private String uuid;
    private Long roomId;
    private Long userId;
    private String message;
    private String url;
    private String type;

    public SpeakRequest(SpeakProto.SpeakRequest speakRequest) {
        this.uuid = speakRequest.getUuid();
        this.roomId = speakRequest.getRoomId();
        this.userId = speakRequest.getUserId();
        this.message = speakRequest.getMessage();
        this.url = speakRequest.getUrl();
        this.type = speakRequest.getType().isEmpty() ? null : speakRequest.getType();
    }
}

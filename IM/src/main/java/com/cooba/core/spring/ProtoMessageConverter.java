package com.cooba.core.spring;

import com.cooba.dto.request.SpeakRequest;
import com.cooba.proto.Speak;
import com.cooba.util.JsonUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

public class ProtoMessageConverter extends AbstractMessageConverter {

    public ProtoMessageConverter() {
        super(new MimeType("application", "protobuf")); // 自定义 MIME 类型
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }


    @NotNull
    @Override
    protected Object convertFromInternal(Message<?> message, @NotNull Class<?> targetClass, @NotNull Object conversionHint) {
        byte[] binaryData = (byte[]) message.getPayload();
        if (targetClass.equals(SpeakRequest.class)){
            try {
                Speak.SpeakRequest speakRequest = Speak.SpeakRequest.parseFrom(binaryData);
                return new SpeakRequest(speakRequest);
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException(e);
            }
        }
        throw new ClassCastException();
    }

    @NotNull
    @Override
    protected Object convertToInternal(@NotNull Object payload, @NotNull MessageHeaders headers, @NotNull Object conversionHint) {
        String json = JsonUtil.toJson(payload);
//        return LZ4Util.compress(json);
        return json.getBytes();
    }
}


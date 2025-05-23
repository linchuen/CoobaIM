package com.cooba.core.spring;

import com.cooba.dto.request.SpeakRequest;
import com.cooba.entity.Chat;
import com.cooba.proto.ChatProto;
import com.cooba.proto.SpeakProto;
import com.google.protobuf.InvalidProtocolBufferException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

public class ProtoMessageConverter extends AbstractMessageConverter {

    public ProtoMessageConverter() {
        super(new MimeType("application", "octet-stream"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }


    @NotNull
    @Override
    protected Object convertFromInternal(Message<?> message, @NotNull Class<?> targetClass, @Nullable Object conversionHint) {
        byte[] binaryData = (byte[]) message.getPayload();
        if (targetClass.equals(SpeakRequest.class)) {
            try {
                SpeakProto.SpeakRequest speakRequest = SpeakProto.SpeakRequest.parseFrom(binaryData);
                return new SpeakRequest(speakRequest);
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException(e);
            }
        }
        throw new ClassCastException();
    }

    @NotNull
    @Override
    protected Object convertToInternal(@NotNull Object payload, @Nullable MessageHeaders headers, @Nullable Object conversionHint) {
        if (payload instanceof Chat chat) {
            return buildChatProto(chat);
        }
        if (payload instanceof byte[] chatProto) {
            return chatProto;
        }
        throw new ClassCastException();
    }

    public static byte[] buildChatProto(Chat chat) {
        ChatProto.ChatInfo chatInfo = ChatProto.ChatInfo.newBuilder()
                .setUuid(chat.getUuid())
                .setId(String.valueOf(chat.getId()))
                .setUserId(chat.getUserId())
                .setName(chat.getName())
                .setRoomId(chat.getRoomId())
                .setMessage(chat.getMessage() == null ? "" : chat.getMessage())
                .setType(chat.getType().name())
                .setUrl(chat.getUrl() == null ? "" : chat.getUrl())
                .setSuccess(true)
                .setCreatedTime(chat.getCreatedTime().toString())
                .build();
        return chatInfo.toByteArray();
    }

    public static ChatProto.ChatInfo readChatProto(byte[] bytes) throws InvalidProtocolBufferException {
        return ChatProto.ChatInfo.parseFrom(bytes);
    }
}


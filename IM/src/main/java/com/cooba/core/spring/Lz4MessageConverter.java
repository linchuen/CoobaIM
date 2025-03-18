package com.cooba.core.spring;

import com.cooba.util.JsonUtil;
import com.cooba.util.LZ4Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.jpountz.lz4.LZ4Factory;
import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

import java.nio.charset.StandardCharsets;

public class Lz4MessageConverter extends AbstractMessageConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final LZ4Factory factory = LZ4Factory.fastestInstance();

    public Lz4MessageConverter() {
        super(new MimeType("application", "lz4-json")); // 自定义 MIME 类型
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }


    @NotNull
    @Override
    protected Object convertFromInternal(Message<?> message, @NotNull Class<?> targetClass, @NotNull Object conversionHint) {
        byte[] compressedData = (byte[]) message.getPayload();
        String json = new String(compressedData);
//        String json = LZ4Util.decompress(compressedData);
        return JsonUtil.fromJson(json, targetClass);
    }

    @NotNull
    @Override
    protected Object convertToInternal(@NotNull Object payload, @NotNull MessageHeaders headers, @NotNull Object conversionHint) {
        String json = JsonUtil.toJson(payload);
//        return LZ4Util.compress(json);
        return json.getBytes();
    }
}


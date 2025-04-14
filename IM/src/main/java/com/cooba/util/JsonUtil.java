package com.cooba.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;


public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    @SneakyThrows
    public static String toJson(Object obj) {
        return objectMapper.writeValueAsString(obj);
    }

    @SneakyThrows
    public static byte[] toJsonByte(Object obj) {
        return objectMapper.writeValueAsBytes(obj);
    }

    @SneakyThrows
    public static <T> T fromJson(String json, Class<T> clazz) {
        return objectMapper.readValue(json, clazz);
    }

    @SneakyThrows
    public static <T> T fromJson(byte[] json, Class<T> clazz) {
        return objectMapper.readValue(json, clazz);
    }
}

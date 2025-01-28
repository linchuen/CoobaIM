package com.cooba.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;


public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();


    @SneakyThrows
    public static String toJson(Object obj) {
        return objectMapper.writeValueAsString(obj);
    }

    @SneakyThrows
    public static <T> T fromJson(String json, Class<T> clazz) {
        return objectMapper.readValue(json, clazz);
    }
}

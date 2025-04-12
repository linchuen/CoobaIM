package com.cooba.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class KryoUtil {
    private static final Kryo kryo = new Kryo();

    public static void register(Class<?> clazz) {
        kryo.register(clazz);
    }

    public static <T> byte[] write(T t) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Output output = new Output(byteArrayOutputStream);
        kryo.writeClassAndObject(output, t);
        output.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static <T> T read(byte[] bytes, Class<T> tClass) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Input input = new Input(byteArrayInputStream);
        T object = kryo.readObject(input, tClass);
        input.close();
        return object;
    }
}

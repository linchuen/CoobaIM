package com.cooba.util;

import com.cooba.core.kafka.KafkaStompSocketConnection;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.springframework.util.Assert;

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

    public static void main(String[] args) {
        KryoUtil.register(KafkaStompSocketConnection.EventData.class);

        KafkaStompSocketConnection.EventData eventData = new KafkaStompSocketConnection.EventData("123456", "test");
        byte[] bytes = KryoUtil.write(eventData);

        KafkaStompSocketConnection.EventData read = KryoUtil.read(bytes, KafkaStompSocketConnection.EventData.class);
        System.out.println("destination = " + read.getDestination());
        System.out.println("payload = " + read.getPayload());

        assert read.getDestination().equals("123456");
        System.out.println("true");
    }
}

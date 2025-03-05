package com.cooba;

import com.cooba.entity.User;
import io.protostuff.runtime.RuntimeSchema;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ProtoGenerator {
    public static void main(String[] args) {
        // 取得 Java 類的 Schema
        RuntimeSchema<User> schema = RuntimeSchema.createFrom(User.class);

        // 生成 .proto 格式的 Schema
        String protoSchema = generateProto(schema);

        // 輸出 .proto 內容
        System.out.println(protoSchema);

        // 將 .proto 內容寫入文件
        try (FileWriter writer = new FileWriter("user.proto")) {
            writer.write(protoSchema);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generateProto(RuntimeSchema<?> schema) {
        StringBuilder sb = new StringBuilder();
        sb.append("syntax = \"proto3\";\n\n");
        sb.append("message ").append(schema.typeClass().getSimpleName()).append(" {\n");

        schema.getFields().forEach(field -> {
            sb.append("    ")
                    .append(getProtoType(field.getClass()))
                    .append(" ")
                    .append(field.name)
                    .append(" = ")
                    .append(field.number)
                    .append(";\n");
        });

        sb.append("}");
        return sb.toString();
    }

    private static String getProtoType(Class<?> javaType) {
        if (javaType == String.class) return "string";
        if (javaType == int.class || javaType == Integer.class) return "int32";
        if (javaType == long.class || javaType == Long.class) return "int64";
        if (javaType == boolean.class || javaType == Boolean.class) return "bool";
        if (javaType == float.class || javaType == Float.class) return "float";
        if (javaType == double.class || javaType == Double.class) return "double";
        return "string"; // 預設為 string
    }
}


package com.cooba;

import com.cooba.entity.Chat;
import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClickHouseSQLGenerator {
    public static String generateCreateTableSQL(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class must be annotated with @Entity");
        }

        Table tableAnnotation = clazz.getAnnotation(Table.class);
        String tableName = !tableAnnotation.name().isEmpty()
                ? tableAnnotation.name()
                : clazz.getSimpleName().toLowerCase();

        StringBuilder createTableSQL = new StringBuilder("CREATE TABLE ").append(tableName).append(" (\n");
        List<String> orderByColumns = new ArrayList<>();

        for (Index index : tableAnnotation.indexes()) {
            if (!index.columnList().isEmpty()) {
                String[] columns = index.columnList().split(", ");
                orderByColumns.addAll(Arrays.asList(columns));
            }
            break;
        }


        String idColumn = null;

        for (Field field : clazz.getDeclaredFields()) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            Id idAnnotation = field.getAnnotation(Id.class);
            Enumerated enumAnnotation = field.getAnnotation(Enumerated.class);
            String columnName = field.getName();
            String columnType;

            if (enumAnnotation != null) {
                columnType = "Int8"; // Enums are stored as Int8
            } else {
                columnType = mapJavaTypeToClickHouseType(field.getType());
            }

            if (idAnnotation != null) {
                columnType = "UInt64";
                idColumn = columnName;
            }

            createTableSQL.append("    ").append(columnName).append(" ").append(columnType).append(",\n");
        }

        createTableSQL.setLength(createTableSQL.length() - 2);
        createTableSQL.append("\n)\nENGINE = MergeTree()\nPARTITION BY toYYYYMM(createdTime)\nORDER BY (");

        if (idColumn != null && orderByColumns.isEmpty()) {
            orderByColumns.add(idColumn);
        }

        createTableSQL.append(String.join(", ", orderByColumns)).append(");");

        return createTableSQL.toString();
    }

    private static String mapJavaTypeToClickHouseType(Class<?> javaType) {
        if (javaType == Long.class) {
            return "Int64";
        } else if (javaType == Integer.class) {
            return "Int32";
        } else if (javaType == Short.class) {
            return "Int16";
        } else if (javaType == Byte.class) {
            return "Int8";
        } else if (javaType == Double.class) {
            return "Float64";
        } else if (javaType == Float.class) {
            return "Float32";
        } else if (javaType == Boolean.class) {
            return "UInt8";
        } else if (javaType == String.class) {
            return "String";
        } else if (javaType == java.time.LocalDate.class) {
            return "Date";
        } else if (javaType == java.time.LocalDateTime.class) {
            return "DateTime DEFAULT now()";
        }
        return "String";
    }

    public static void main(String[] args) {
        String sql = generateCreateTableSQL(Chat.class);
        System.out.println("\nGenerated SQL:\n" + sql);
    }
}


package com.cooba;

import com.cooba.entity.Chat;
import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ClickHouseSQLGenerator {
    public static String generateCreateTableSQL(Class<?> clazz) {
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        String tableName = "t_" + toSnakeCase(tableAnnotation.name().isEmpty() ? clazz.getSimpleName() : tableAnnotation.name());

        StringBuilder createTableSQL = new StringBuilder("CREATE TABLE ").append(tableName).append(" (\n");
        String idColumn = "id";

        for (Field field : clazz.getDeclaredFields()) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            boolean isNullable = columnAnnotation == null || columnAnnotation.nullable();
            Id idAnnotation = field.getAnnotation(Id.class);
            Enumerated enumAnnotation = field.getAnnotation(Enumerated.class);
            String columnName = toSnakeCase(field.getName());
            String columnType;

            if (idAnnotation != null) {
                columnType = "UInt64";
                idColumn = columnName;
                isNullable = false; // ID 不應該為 NULL
            } else if (enumAnnotation != null) {
                EnumType enumType = enumAnnotation.value();
                columnType = enumType == EnumType.STRING ? "String" : "Int8"; // Enums are stored as Int8
            } else {
                columnType = mapJavaTypeToClickHouseType(field.getType());
            }
            createTableSQL.append("    ").append(columnName).append(" ").append(columnType);
            if (isNullable) {
                createTableSQL.append(" NULL");
            } else {
                createTableSQL.append(" NOT NULL");
            }
            createTableSQL.append(",\n");
        }

        createTableSQL.setLength(createTableSQL.length() - 2);
        createTableSQL.append("\n)")
                .append("\nENGINE = MergeTree()")
                .append("\nPARTITION BY toYYYYMM(created_time)")
                .append("\nORDER BY (").append(idColumn).append(");\n");

        // Append projections based on indexes
        for (Index index : tableAnnotation.indexes()) {
            String projectionName = "proj_" + index.name();
            String[] columns = index.columnList().split(",");
            List<String> projectionColumns = new ArrayList<>();
            for (String col : columns) {
                projectionColumns.add(toSnakeCase(col.trim()));
            }
            projectionColumns.add(idColumn);

            createTableSQL.append("\nALTER TABLE ").append(tableName)
                    .append(" ADD PROJECTION ").append(projectionName)
                    .append(" \n( SELECT ")
                    .append(" * ")
                    .append(" ORDER BY ").append(String.join(", ", projectionColumns))
                    .append(" );");
        }
        return createTableSQL.toString();
    }

    private static String toSnakeCase(String str) {
        return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    private static String mapJavaTypeToClickHouseType(Class<?> javaType) {
        if (javaType == Long.class || javaType == long.class) {
            return "Int64";
        } else if (javaType == Integer.class || javaType == int.class) {
            return "Int32";
        } else if (javaType == Short.class || javaType == short.class) {
            return "Int16";
        } else if (javaType == Byte.class || javaType == byte.class) {
            return "Int8";
        } else if (javaType == Double.class || javaType == double.class) {
            return "Decimal(18,5)";
        } else if (javaType == Float.class || javaType == float.class) {
            return "Decimal(18,5)";
        } else if (javaType == BigDecimal.class) {
            return "Decimal(18,5)";
        } else if (javaType == Boolean.class || javaType == boolean.class) {
            return "Bool";
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

package com.cooba;

import com.google.common.reflect.ClassPath;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class Hibernate {
    public static void main(String[] args) throws IOException {

        List<? extends Class<?>> list = ClassPath.from(ClassLoader.getSystemClassLoader())
                .getAllClasses()
                .stream()
                .filter(clazz -> clazz.getPackageName().equalsIgnoreCase("com.cooba.entity"))
                .map(ClassPath.ClassInfo::load)
                .collect(Collectors.toList());

        Map<String, Object> config = getConfig();

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(config) // 加載配置文件
                .build();

        try {
            // 創建 Metadata
            MetadataSources metadataSources = new MetadataSources(serviceRegistry);
            for (Class<?> c : list) {
                metadataSources.addAnnotatedClasses(c);//
            }
            Metadata metadata = metadataSources.buildMetadata();

            // 創建 SchemaExport
            SchemaExport schemaExport = new SchemaExport();
            schemaExport.setDelimiter(";");
            schemaExport.setFormat(true);
            schemaExport.setOverrideOutputFileContent();
            schemaExport.setManageNamespaces(true);
            schemaExport.setOutputFile("schema.sql");

            // 指定目標：控制台輸出和數據庫應用
            schemaExport.createOnly(EnumSet.of(TargetType.SCRIPT), metadata);

        } finally {
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
    }

    private static Map<String, Object> getConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        config.put("hibernate.connection.url", "jdbc:mysql://127.0.0.1:13306/im");
        config.put("hibernate.connection.username", "root");
        config.put("hibernate.connection.password", "example");
        config.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        config.put("hibernate.hbm2ddl.auto", "none");
        config.put("hibernate.show_sql", "true");
        config.put("hibernate.format_sql", "true");
        config.put("hibernate.physical_naming_strategy","com.cooba.CustomPhysicalNamingStrategy");
        return config;
    }
}

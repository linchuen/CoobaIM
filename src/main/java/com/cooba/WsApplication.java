package com.cooba;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan({"com.cooba.mapper"})
@EnableConfigurationProperties
public class WsApplication {
    public static void main(String[] args) {
        SpringApplication.run(WsApplication.class, args);
    }
}
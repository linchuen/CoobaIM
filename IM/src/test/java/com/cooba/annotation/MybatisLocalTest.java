package com.cooba.annotation;


import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.cooba.config.MockConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Import(value = {MockConfig.class})
@ActiveProfiles(value = "test")
@MapperScan({"com.cooba.mapper"})
@ComponentScan({"com.cooba.repository"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisPlusTest
@EnableConfigurationProperties
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MybatisLocalTest {
}
package com.cooba.annotation;

import jakarta.persistence.Entity;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Entity
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface IMEntity {
    String value() default "";

}

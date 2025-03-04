package com.cooba.annotation;

import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Repository;

import java.lang.annotation.*;

@Repository
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface DataManipulateLayer {
    String value() default "";

}

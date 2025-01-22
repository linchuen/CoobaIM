package com.cooba.annotation;

import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Service
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface BehaviorLayer {
    String value() default "";

}

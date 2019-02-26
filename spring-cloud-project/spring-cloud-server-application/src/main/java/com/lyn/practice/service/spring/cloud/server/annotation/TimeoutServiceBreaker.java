package com.lyn.practice.service.spring.cloud.server.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TimeoutServiceBreaker {

    long timeout() default 1000;
}

package com.lyn.practice.service.spring.cloud.client.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface RestClient {

    /**
     * 服务提供者名称 对应服务提供者 application.properties中配置的spring.application.name
     * @return
     */
    @AliasFor("name")
    String value() default "";

    /**
     * 服务提供者名称 对应服务提供者 application.properties中配置的spring.application.name
     * @return
     */
    @AliasFor("value")
    String name() default "";

}

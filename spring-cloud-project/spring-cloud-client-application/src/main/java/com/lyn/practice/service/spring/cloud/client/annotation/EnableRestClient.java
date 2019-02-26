package com.lyn.practice.service.spring.cloud.client.annotation;

import com.lyn.practice.service.spring.cloud.client.register.RestClientRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RestClientRegistrar.class)
public @interface EnableRestClient {


    /**
     * 客户端类
     * 即 使用@RestClient注释的类
     * @return
     */
    Class<?>[] clients() default {};

    /**
     * 如果clients为空，则扫描路径起作用，
     * 开始扫描该路径下的所有被@RestClient注释的类
     * 如果为空，则直接开始扫描根路径
     * 扫描路径
     * @return
     */
    String basePackageScan() default "";

}

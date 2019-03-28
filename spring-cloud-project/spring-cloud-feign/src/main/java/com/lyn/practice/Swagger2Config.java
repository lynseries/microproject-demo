package com.lyn.practice;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class Swagger2Config {

    @Bean
    public Docket createRestApi(){

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                .select()
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                ;
    }

    //    public ApiInfo(String title, String description, String version, String termsOfServiceUrl, Contact contact, String license, String licenseUrl) {
    private ApiInfo apiInfo(){
        Contact DEFAULT_CONTACT = new Contact("", "", "");

        return new ApiInfo("Feign测试","更多Spring Boot相关文章请关注：http://blog.didispace.com/","1.0.0","",DEFAULT_CONTACT,"1","1");
    }

    private List<ApiKey> securitySchemes() {
        return new ArrayList(Collections.singleton(new ApiKey("Authorization", "Authorization", "header")));
    }

    private List<SecurityContext> securityContexts() {
        return new ArrayList(
                Collections.singleton(SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!login).*$"))
                        .build())
        );
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return new ArrayList(Collections.singleton(new SecurityReference("Authorization", authorizationScopes)));
    }

}

package com.lyn.practice.autoconfig;

import com.lyn.practice.bean.HasBean;
import com.lyn.practice.controller.SayBySqlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MappingProperties.class)
@ConditionalOnBean(value = MappingConfiguration.Marker.class)
//@ConditionalOnProperty(prefix = "",name = "select.by.sql",matchIfMissing = true)
@ConditionalOnProperty(name = "select.by.sql",havingValue = "abc")
public class MappingAutoConfiguration {

    @Autowired
    private MappingProperties properties;


    @Bean
    public SayService sayService(){

        return new SayService(properties.getSql());
    }



}

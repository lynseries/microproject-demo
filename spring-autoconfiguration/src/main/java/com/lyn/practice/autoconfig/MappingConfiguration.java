package com.lyn.practice.autoconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class MappingConfiguration {

    class Marker{

    }

    @Bean
    public Marker enableMappingConfigBean(){
        return new Marker();
    }
}

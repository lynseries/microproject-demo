package com.lyn.practice.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("select.order")
public class MappingProperties {

    private String sql;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}

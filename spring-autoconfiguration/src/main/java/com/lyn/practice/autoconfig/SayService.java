package com.lyn.practice.autoconfig;


public class SayService {

    private String sql;

    public SayService(String sql){
        this.sql = sql;
    }

    public String sayHello(String message){

        return "hello "+message+",select sql is "+sql;

    }
}

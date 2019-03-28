package com.lyn.practice.api;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "spring-cloud-feign")
public interface LoginApi {


    @GetMapping("login/{username}/{password}")
    public String login(@PathVariable String username,@PathVariable String password);

    @GetMapping("login2")
    public String login2(LoginModel loginModel);

    @PostMapping("doLogin")
    public String doLogin(String username,String password);
}

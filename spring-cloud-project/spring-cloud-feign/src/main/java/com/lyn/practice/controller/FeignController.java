package com.lyn.practice.controller;

import com.lyn.practice.api.LoginApi;
import com.lyn.practice.api.LoginModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "WEB - MallAuthRestController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FeignController implements LoginApi {


    @ApiOperation(httpMethod = "GET", value = "登录")
    @Override
    public String login(@PathVariable String username, @PathVariable String password) {
        return "hello "+username+",your password is " + password;
    }

    @ApiOperation(httpMethod = "GET", value = "登录2")
    public String login2(LoginModel loginModel) {
        return "hello "+loginModel.getUsername()+",your password is " + loginModel.getPassword();
    }

    @Override
    public String doLogin(String username, String password) {
        return null;
    }
}

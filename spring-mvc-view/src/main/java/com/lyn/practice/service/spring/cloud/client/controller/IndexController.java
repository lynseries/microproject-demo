package com.lyn.practice.service.spring.cloud.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by liuyanan on 2018-09-03.
 */
@Controller
public class IndexController {

    @RequestMapping(value = {"","/","index"})
    public String toIndex(Model model){
        model.addAttribute("message","hello world!!");
        return "index";
    }
}

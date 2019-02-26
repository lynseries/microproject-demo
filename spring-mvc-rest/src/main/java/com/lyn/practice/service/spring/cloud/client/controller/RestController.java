package com.lyn.practice.service.spring.cloud.client.controller;

import com.lyn.practice.service.spring.cloud.client.model.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liuyanan on 2018-09-13.
 */

@Controller
public class RestController {

    @RequestMapping("sayHello")
    @ResponseBody
    public String helloWorld(){
        return "hello world";
    }


    @RequestMapping("getSomething/{isCache}")
     public ResponseEntity<Person> getSomething(@PathVariable String isCache){

        System.out.println(isCache);
        if("true".equals(isCache)){
            return new ResponseEntity<Person>(HttpStatus.NOT_MODIFIED);
        }else{
            Person person = new Person();
            person.setName("mumu");
            person.setIdCard("1307211930039388990");
            person.setPhoneNum("15810779022");
            return ResponseEntity.ok(person);
        }
    }

    @RequestMapping("getSomething1")
    public ResponseEntity<Person> getSomething1(@RequestParam(required = false,defaultValue = "false") boolean isCache){

        System.out.println(isCache);
        if(isCache){
            return new ResponseEntity<Person>(HttpStatus.NOT_MODIFIED);
        }else{
            Person person = new Person();
            person.setName("liumumu");
            person.setIdCard("1307211930039388990");
            person.setPhoneNum("15810779022");
            return ResponseEntity.ok(person);
        }
    }

}

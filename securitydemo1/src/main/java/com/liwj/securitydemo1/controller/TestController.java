package com.liwj.securitydemo1.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("hello")
    public String add(){
        return "hello";
    }

    @GetMapping("index")
    public String index(){
        return "hello index";
    }

    @GetMapping("update")
    public String update(){
        return "hello update";
    }

}

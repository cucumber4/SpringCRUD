package ru.chassovnikov.com.SpringBootApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    public String helloIndex(){
        return "hello";
    }
}

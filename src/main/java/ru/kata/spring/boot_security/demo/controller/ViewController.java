package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/admin")
    public String adminPage() {
        return "JsonTest";
    }
    @GetMapping("/user")
    public String userPage(){
        return "user";
    }

    @GetMapping("/logoutSuccess")
    public String logout() {
        return "logoutSuccess";
    }
}

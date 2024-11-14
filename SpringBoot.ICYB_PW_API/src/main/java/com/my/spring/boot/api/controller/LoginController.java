package com.my.spring.boot.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class LoginController {

    @GetMapping("/hello")
    public String hello() {
        log.info("Hello, World!");
        return "Hello, World!";
    }
}
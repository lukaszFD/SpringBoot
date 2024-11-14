package com.my.spring.boot.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BasicAuthExampleController {

    @GetMapping("/public/secureAPI")
    public ResponseEntity securedApi(@RequestHeader HttpHeaders headers) {
        if (headers.containsKey(HttpHeaders.AUTHORIZATION)) {
            String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader.startsWith("Basic ")) {
                log.info("Authentication passed");
                return new ResponseEntity<>("Authentication passed", HttpStatus.OK);
            }
        }
        log.warn("Unauthorized");
        return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
    }
}
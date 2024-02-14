package com.setronica.eventing.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class CheckHealthController {

    @GetMapping
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok("I am healthy");
    }
}

package ru.rakhim.banking_system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello Rakhim");
    }
}
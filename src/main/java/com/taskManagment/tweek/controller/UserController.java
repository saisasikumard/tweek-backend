package com.taskManagment.tweek.controller;

import com.taskManagment.tweek.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return new ResponseEntity<>(userService.test(), HttpStatus.CONFLICT);
    }
}

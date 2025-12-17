package com.taskManagment.tweek.controller;

import com.taskManagment.tweek.config.JwtService;
import com.taskManagment.tweek.dto.AuthRequest;
import com.taskManagment.tweek.dto.UserRegisterRequest;
import com.taskManagment.tweek.entity.Users;
import com.taskManagment.tweek.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @Autowired
    UserService userService;
    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return new ResponseEntity<>(userService.test(), HttpStatus.OK);
    }
    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest){
        logger.info("entered authenticated method..,before authentication");
        Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(),authRequest.getPassword()));
        logger.info("entered authenticated method...");
        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUserName());
        }
        else{
            throw new RuntimeException("Invalid UserRequest");
        }
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequest user){
       // user.setRole("ROLE_USER");
        logger.info("Inside addStudent method...");
        return  new ResponseEntity<>(userService.addUser(user),HttpStatus.CREATED);
    }

}

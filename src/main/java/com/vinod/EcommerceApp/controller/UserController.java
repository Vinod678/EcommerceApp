package com.vinod.EcommerceApp.controller;

import com.vinod.EcommerceApp.model.User.UserEntity;
import com.vinod.EcommerceApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserEntity user) {
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserEntity user) {
        if (userService.validateUser(user)) {
            // User authentication successful, you can generate a token or set a session
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
//    http://localhost:8080/users/getUserIdByUserEmail?userEmail=jjkk4@gmail.com
    @GetMapping("/getUserIdByUserEmail")
    public Integer getUserIdByUserEmail(@RequestParam String userEmail){
        Integer userId = userService.getUserIdByUserEmail(userEmail);
        return userId;
    }
}
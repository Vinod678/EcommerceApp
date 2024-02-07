package com.vinod.EcommerceApp.controller;


import com.vinod.EcommerceApp.model.User.UserProfileEntity;
import com.vinod.EcommerceApp.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-profile")
@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @PostMapping("/update/{userId}")
    public ResponseEntity<String> updateUserProfile(@PathVariable Long userId, @RequestBody UserProfileEntity userProfile) {
        try {
            userProfileService.updateUserProfile(userId, userProfile);
            return ResponseEntity.ok("User profile updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

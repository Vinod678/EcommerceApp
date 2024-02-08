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

    @PostMapping("/update/{userEmail}")
    public ResponseEntity<String> updateUserProfileByUserEmail(@PathVariable String userEmail, @RequestBody UserProfileEntity userProfile) {
        try {
            userProfileService.updateUserProfileByUserEmail(userEmail, userProfile);
            return ResponseEntity.ok("User profile updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{userEmail}")
    public ResponseEntity<UserProfileEntity> getUserProfileByEmail(@PathVariable String userEmail) {
        try {
            UserProfileEntity userProfile = userProfileService.getUserProfileByEmail(userEmail);
            return ResponseEntity.ok(userProfile);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

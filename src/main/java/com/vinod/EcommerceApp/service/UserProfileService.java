package com.vinod.EcommerceApp.service;


import com.vinod.EcommerceApp.controller.UserProfileController;
import com.vinod.EcommerceApp.model.User.UserProfileEntity;

public interface UserProfileService {
    void updateUserProfileByUserEmail(String userEmail, UserProfileEntity userProfile);
    UserProfileEntity getUserProfileByEmail(String userEmail);
}

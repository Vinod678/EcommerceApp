package com.vinod.EcommerceApp.service;


import com.vinod.EcommerceApp.model.User.UserProfileEntity;

public interface UserProfileService {
    void updateUserProfile(Long userId,UserProfileEntity userProfile);
}


package com.vinod.EcommerceApp.service;


import com.vinod.EcommerceApp.model.User.UserEntity;
import com.vinod.EcommerceApp.model.User.UserProfileEntity;
import com.vinod.EcommerceApp.repository.UserProfileRepository;
import com.vinod.EcommerceApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Override
    public void updateUserProfileByUserEmail(String userEmail, UserProfileEntity userProfile) {
        // Check if the user with the provided email exists in the UserEntity
        UserEntity userEntity = userRepository.findByUserEmail(userEmail);
        if (userEntity == null) {
            throw new IllegalArgumentException("User with Email " + userEmail + " does not exist.");
        }

        // Set userId and userEmail from UserEntity
        userProfile.setUserEmail(userEmail);
        userProfile.setUserId(userEntity.getId());

        // Save the updated user profile
        userProfileRepository.save(userProfile);
    }


    @Override
    public UserProfileEntity getUserProfileByEmail(String userEmail) {
        Optional<UserProfileEntity> userProfileOptional = userProfileRepository.findByUserEmail(userEmail);
        if (userProfileOptional.isPresent()) {
            return userProfileOptional.get();
        } else {
            throw new IllegalArgumentException("User profile not found for email: " + userEmail);
        }
    }

}

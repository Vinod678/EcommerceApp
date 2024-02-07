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
    public void updateUserProfile(Long userId, UserProfileEntity userProfile) {
        // Check if the userId exists in the UserEntity
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist.");
        }

        // Set userId and userEmail from UserEntity
        userProfile.setUserId(userId);
        userProfile.setUserEmail(userOptional.get().getUserEmail());

        // Save the updated user profile
        userProfileRepository.save(userProfile);
    }
}

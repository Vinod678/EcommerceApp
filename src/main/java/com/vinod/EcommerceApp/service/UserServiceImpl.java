package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.model.User.UserEntity;
import com.vinod.EcommerceApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerUser(UserEntity user) {
        // Check if the Email is unique
        if (userRepository.findByUserEmail(user.getUserEmail()) != null) {
            throw new RuntimeException("Email already exists. Please choose a different Email.");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    @Override
    public boolean validateUser(UserEntity user) {
        // Retrieve the user from the database based on the Email
        UserEntity storedUser = userRepository.findByUserEmail(user.getUserEmail());

        // Check if the user exists and the provided password matches
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return storedUser != null && bCryptPasswordEncoder.matches(user.getPassword(), storedUser.getPassword());
    }
    @Override
    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
    }

@Override
public Integer getUserIdByUserEmail(String userEmail) {
    UserEntity user = userRepository.findByUserEmail(userEmail);
    if (user != null) {
        return user.getId().intValue();
    } else {
        throw new IllegalArgumentException("User not found with Email: " + userEmail);
    }
}


}
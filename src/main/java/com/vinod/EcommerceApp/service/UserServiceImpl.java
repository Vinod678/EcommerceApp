package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.model.User.UserEntity;
import com.vinod.EcommerceApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerUser(UserEntity user) {
        // You can add logic for user validation before saving to the database
        // Check if the username is unique
        if (userRepository.findByUserName(user.getUserName()) != null) {
            throw new RuntimeException("Username already exists. Please choose a different username.");
        }
        userRepository.save(user);
    }

    @Override
    public boolean validateUser(UserEntity user) {
        // Retrieve the user from the database based on the username
        UserEntity storedUser = userRepository.findByUserName(user.getUserName());

        // Check if the user exists and the provided password matches
        return storedUser != null && storedUser.getPassword().equals(user.getPassword());
    }
}
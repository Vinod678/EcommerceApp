package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.model.User.UserEntity;

public interface UserService {
    void registerUser(UserEntity user);

    boolean validateUser(UserEntity user);
}

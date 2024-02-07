package com.vinod.EcommerceApp.service;


import com.vinod.EcommerceApp.repository.UserProfileRepository;
import com.vinod.EcommerceApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileServiceImpl.class);


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository customerRepository;

}

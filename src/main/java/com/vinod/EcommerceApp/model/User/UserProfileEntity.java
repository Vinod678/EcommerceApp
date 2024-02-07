package com.vinod.EcommerceApp.model.User;

import jakarta.persistence.*;

@Entity
public class UserProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userEmail;
    private String userName;
    private String userPhoneNumber;
    private String userAddress;




}
package com.vinod.EcommerceApp.model.User;

import com.vinod.EcommerceApp.model.CartTable.CartTable;
import jakarta.persistence.*;

import java.util.Set;
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;
    private String userEmail;
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfileEntity userProfile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<CartTable> cartEntries; // Add reference to CartTable
    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public UserProfileEntity getUserProfile() {
//        return userProfile;
//    }
//
//    public void setUserProfile(UserProfileEntity userProfile) {
//        this.userProfile = userProfile;
//    }
    public Set<CartTable> getCartEntries() {
        return cartEntries;
}

    public void setCartEntries(Set<CartTable> cartEntries) {
        this.cartEntries = cartEntries;
    }
}
package com.vinod.EcommerceApp.repository;

import com.vinod.EcommerceApp.model.User.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {
    Optional<UserProfileEntity> findByUserEmail(String userEmail);


}

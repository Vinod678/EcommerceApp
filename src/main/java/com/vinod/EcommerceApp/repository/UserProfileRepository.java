package com.vinod.EcommerceApp.repository;

import com.vinod.EcommerceApp.model.User.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {

}

package com.vinod.EcommerceApp.repository;

import com.vinod.EcommerceApp.model.CartTable.CartTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartTable, Long> {
    Optional<CartTable> findByProductProductID(String productId);
    List<CartTable> findByUserId(Long userId);
    Optional<CartTable> findByUserIdAndProduct_ProductID(Long userId, String productId);
}


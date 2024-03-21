package com.vinod.EcommerceApp.repository;

import com.vinod.EcommerceApp.model.ProductTable.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ProductReview, Long> {
    List<ProductReview> findByProduct_ProductID(String productID);

    Optional<ProductReview> findByUserIdAndProduct_ProductID(Long userId, String productId);
}





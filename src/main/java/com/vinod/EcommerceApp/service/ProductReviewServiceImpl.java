package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.model.CartTable.CartTable;
import com.vinod.EcommerceApp.model.ProductTable.ProductReview;
import com.vinod.EcommerceApp.model.User.UserEntity;
import com.vinod.EcommerceApp.repository.ProductReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    @Autowired
    private ProductReviewRepository productReviewRepository;

    @Override
    public List<ProductReview> getReviewsForProduct(String productId) {
        return productReviewRepository.findByProduct_ProductID(productId);
    }

    @Override
    public void addReview(ProductReview productReview, UserEntity user) {
        productReview.setUser(user); // Set the user property
        checkReviewExists(productReview, user.getId()); // Pass userId to check for duplicates
        productReviewRepository.save(productReview);
    }
    private void checkReviewExists(ProductReview productReview, Long userId){
        String productIdString = productReview.getProduct().getProductID();
        Long productId = Long.valueOf(productIdString);
        // Check if any product review already exists for the given user and product ID
        Optional<ProductReview> existingReview = productReviewRepository.findByUserIdAndProduct_ProductID(userId, String.valueOf(productId));

        if (existingReview.isPresent()) {
            throw new IllegalArgumentException("Review already given");
        }
    }

}

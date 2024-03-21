package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.model.ProductTable.ProductReview;
import com.vinod.EcommerceApp.model.User.UserEntity;
import com.vinod.EcommerceApp.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<ProductReview> getReviewsForProduct(String productId) {
        return reviewRepository.findByProduct_ProductID(productId);
    }

    @Override
    public void addReview(ProductReview productReview, UserEntity user) {
        productReview.setUser(user); // Set the user property
        checkReviewExists(productReview, user.getId()); // Pass userId to check for duplicates
        reviewRepository.save(productReview);
    }
    private void checkReviewExists(ProductReview productReview, Long userId){
        String productIdString = productReview.getProduct().getProductID();
        Long productId = Long.valueOf(productIdString);
        // Check if any product review already exists for the given user and product ID
        Optional<ProductReview> existingReview = reviewRepository.findByUserIdAndProduct_ProductID(userId, String.valueOf(productId));

        if (existingReview.isPresent()) {
            throw new IllegalArgumentException("Review already given");
        }
    }

}

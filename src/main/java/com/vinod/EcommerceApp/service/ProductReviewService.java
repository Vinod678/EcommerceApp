package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.model.CartTable.CartTable;
import com.vinod.EcommerceApp.model.ProductTable.ProductReview;
import com.vinod.EcommerceApp.model.User.UserEntity;

import java.util.List;

public interface ProductReviewService {
    void addReview(ProductReview productReview, UserEntity user);
    List<ProductReview> getReviewsForProduct(String productId);

}

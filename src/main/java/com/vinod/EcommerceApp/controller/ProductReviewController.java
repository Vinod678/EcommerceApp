package com.vinod.EcommerceApp.controller;

import com.vinod.EcommerceApp.DTO.ProductReviewDTO;
import com.vinod.EcommerceApp.DTO.ProductReviewResponseDTO;
import com.vinod.EcommerceApp.model.CartTable.CartTable;
import com.vinod.EcommerceApp.model.ProductTable.ProductReview;
import com.vinod.EcommerceApp.model.User.UserEntity;
import com.vinod.EcommerceApp.model.User.UserProfileEntity;
import com.vinod.EcommerceApp.service.CartTableService;
import com.vinod.EcommerceApp.service.ProductReviewService;
import com.vinod.EcommerceApp.service.UserProfileService;
import com.vinod.EcommerceApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/product-reviews")
@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
public class ProductReviewController {

    @Autowired
    private ProductReviewService productReviewService;

    @Autowired
    private UserService userService;
    @Autowired
    private UserProfileService userProfileService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/addReview")
    public ResponseEntity<String> addReview(@RequestBody ProductReview productReview, @RequestParam Long userId) {
        try {
            UserEntity user = userService.getUserById(userId);
            productReviewService.addReview(productReview, user);
            logger.info("product review given Successfully");
            return ResponseEntity.ok("product review given Successfully");
        } catch (IllegalArgumentException ex) {
            // Handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + ex.getMessage());
        }
    }


    @GetMapping("/get")
    public ResponseEntity<ProductReviewResponseDTO> getReviewsForProduct(@RequestParam String productId) {
        List<ProductReview> reviews = productReviewService.getReviewsForProduct(productId);

        // Calculate total number of reviews
        int totalReviews = reviews.size();

        // Calculate average rating
        double averageRating = 0;
        if (!reviews.isEmpty()) {
            int sumRatings = reviews.stream().mapToInt(ProductReview::getRating).sum();
            averageRating = (double) sumRatings / totalReviews;
        }

        // Convert reviews to DTOs
        List<ProductReviewDTO> reviewDTOs = reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        // Create response DTO
        ProductReviewResponseDTO responseDTO = new ProductReviewResponseDTO();
        responseDTO.setTotalReviews(totalReviews);
        responseDTO.setAverageRating(averageRating);
        responseDTO.setReviews(reviewDTOs);

        return ResponseEntity.ok(responseDTO);
    }


    private ProductReviewDTO convertToDTO(ProductReview review) {
        ProductReviewDTO dto = new ProductReviewDTO();
        dto.setReviewID(review.getReviewID());
        dto.setRating(review.getRating());
        dto.setReviewText(review.getReviewText());
        dto.setProductID(review.getProduct().getProductID());
        dto.setUserID(review.getUser().getId());

        // Fetch UserProfileEntity associated with the UserEntity
        UserProfileEntity userProfile = userProfileService.getUserProfileByEmail(review.getUser().getUserEmail());
        if (userProfile != null) {
            dto.setUserName(userProfile.getUserName());
        } else {
            // Set default value or handle the case when UserProfileEntity is null
            dto.setUserName("Unknown");
        }
        return dto;
    }

}

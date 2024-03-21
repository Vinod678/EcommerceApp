package com.vinod.EcommerceApp.controller;

import com.vinod.EcommerceApp.DTO.ReviewDTO;
import com.vinod.EcommerceApp.DTO.ReviewResponseDTO;
import com.vinod.EcommerceApp.model.ProductTable.ProductReview;
import com.vinod.EcommerceApp.model.User.UserEntity;
import com.vinod.EcommerceApp.model.User.UserProfileEntity;
import com.vinod.EcommerceApp.service.ReviewService;
import com.vinod.EcommerceApp.service.UserProfileService;
import com.vinod.EcommerceApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/product-reviews")
@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;
    @Autowired
    private UserProfileService userProfileService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/addReview")
    public ResponseEntity<String> addReview(@RequestBody ProductReview productReview, @RequestParam Long userId) {
        try {
            Date currentDate = new Date();
            // Set the reviewDate field in the ProductReview object
            productReview.setReviewDate(currentDate);
            UserEntity user = userService.getUserById(userId);
            reviewService.addReview(productReview, user);
            logger.info("product review given Successfully");
            return ResponseEntity.ok("product review given Successfully");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + ex.getMessage());
        }
    }


    @GetMapping("/get")
    public ResponseEntity<ReviewResponseDTO> getReviewsForProduct(@RequestParam String productId) {
        List<ProductReview> reviews = reviewService.getReviewsForProduct(productId);

        // Calculate total number of reviews
        int totalReviews = reviews.size();

        // Calculate average rating
        double averageRating = 0;
        if (!reviews.isEmpty()) {
            int sumRatings = reviews.stream().mapToInt(ProductReview::getRating).sum();
            averageRating = (double) sumRatings / totalReviews;
        }

        // Convert reviews to DTOs
        List<ReviewDTO> reviewDTOs = reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        // Create response DTO
        ReviewResponseDTO responseDTO = new ReviewResponseDTO();
        responseDTO.setTotalReviews(totalReviews);
        responseDTO.setAverageRating(averageRating);
        responseDTO.setReviews(reviewDTOs);

        return ResponseEntity.ok(responseDTO);
    }


    private ReviewDTO convertToDTO(ProductReview review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setReviewID(review.getReviewID());
        dto.setRating(review.getRating());
        dto.setReviewText(review.getReviewText());


        // Set reviewDate in DTO
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dto.setReviewDate(dateFormat.format(review.getReviewDate()));

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

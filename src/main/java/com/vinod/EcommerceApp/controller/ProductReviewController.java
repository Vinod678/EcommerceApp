package com.vinod.EcommerceApp.controller;

import com.vinod.EcommerceApp.model.CartTable.CartTable;
import com.vinod.EcommerceApp.model.ProductTable.ProductReview;
import com.vinod.EcommerceApp.model.User.UserEntity;
import com.vinod.EcommerceApp.service.CartTableService;
import com.vinod.EcommerceApp.service.ProductReviewService;
import com.vinod.EcommerceApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/product-reviews")
@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
public class ProductReviewController {

    @Autowired
    private ProductReviewService productReviewService;

    @Autowired
    private UserService userService;

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


//    @PostMapping("/add")
//    public ResponseEntity<ProductReview> addReview(@RequestBody ProductReview productReview) {
//        ProductReview addedReview = productReviewService.addReview(productReview);
//        return ResponseEntity.ok(addedReview);
//    }

    @GetMapping("/get")
    public ResponseEntity<List<ProductReview>> getReviewsForProduct(@RequestParam String productId) {
        List<ProductReview> reviews = productReviewService.getReviewsForProduct(productId);
        return ResponseEntity.ok(reviews);
    }

    // Other controller methods for managing product reviews
}

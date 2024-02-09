package com.vinod.EcommerceApp.controller;

import com.vinod.EcommerceApp.model.User.UserEntity;
import com.vinod.EcommerceApp.service.CartTableService;
import com.vinod.EcommerceApp.service.UserService;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vinod.EcommerceApp.model.CartTable.CartTable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
public class CartController {

    @Autowired
    private CartTableService cartTableService;
    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart(@RequestBody CartTable cartItem, @RequestParam Long userId) {
        try {
            UserEntity user = userService.getUserById(userId);
            cartTableService.addToCart(cartItem, user);
            logger.info("Item added to cart Successfully");
            return ResponseEntity.ok("Item added to cart successfully");
        } catch (IllegalArgumentException ex) {
            // Handle exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + ex.getMessage());
        }
    }
    @GetMapping("/getAllCartItems")
    public ResponseEntity<List<CartTable>> getAllCartItems(@RequestParam Long userId) {
        List<CartTable> cartItems = cartTableService.getAllCartItemsByUserId(userId);
        // Calculate total count
        int totalCount = 0;
        for (CartTable cartItem : cartItems) {
            totalCount += cartItem.getQuantity();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(totalCount));
        return new ResponseEntity<>(cartItems, headers, HttpStatus.OK);
    }

    @DeleteMapping("/clearCart")
    public ResponseEntity<String> clearCart(@RequestParam Long userId) {
        cartTableService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared successfully");
    }
    @DeleteMapping("/clearCartItem/{cartID}")
    public ResponseEntity<String> clearCartItem(@PathVariable Long cartID) {
        cartTableService.clearCartSingleItem(cartID);
        return ResponseEntity.ok("Cart item cleared successfully");
    }

    @GetMapping("/subTotalAmount")
    public ResponseEntity<Double> subTotalAmount(@RequestParam Long userId) {
        Double subTotalCost = cartTableService.subTotalCost(userId);
        return ResponseEntity.ok(subTotalCost);
    }

}
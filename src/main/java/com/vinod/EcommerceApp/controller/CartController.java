package com.vinod.EcommerceApp.controller;

import com.vinod.EcommerceApp.service.CartTableService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart(@RequestBody CartTable cartItem) {
        cartTableService.addToCart(cartItem);
        return ResponseEntity.ok("Item added to cart successfully");
    }

    @GetMapping("/getAllCartItems")
    public ResponseEntity<List<CartTable>> getAllCartItems() {
        List<CartTable> cartItems = cartTableService.getAllCartItems();
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/clearCart")
    public ResponseEntity<String> clearCart() {
        cartTableService.clearCart();
        return ResponseEntity.ok("Cart cleared successfully");
    }
}

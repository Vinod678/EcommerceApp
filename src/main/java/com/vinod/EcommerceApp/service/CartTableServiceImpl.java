package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.model.CartTable.CartTable;
import com.vinod.EcommerceApp.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartTableServiceImpl implements CartTableService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public void addToCart(CartTable cartItem) {
        validateCartItem(cartItem);
        handleDuplicateCartItem(cartItem);
        cartRepository.save(cartItem);
    }

    private void validateCartItem(CartTable cartItem) {
        // Check if the quantity is valid
        if (cartItem.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid quantity: Quantity must be greater than zero.");
        }
    }

    private void handleDuplicateCartItem(CartTable cartItem) {
        String productIdString = cartItem.getProduct().getProductID();
        Long productId = Long.valueOf(productIdString);

        Optional<CartTable> existingCartItem = cartRepository.findByProductProductID(String.valueOf(productId));

        // If a cart item with the same product ID already exists
        if (existingCartItem.isPresent()) {
            // For example, you can update the quantity of the existing item instead of adding a new one
            throw new IllegalArgumentException("Duplicate cart item: Product already exists in the cart.");
        }
    }

    @Override
    public List<CartTable> getAllCartItems() {
        return cartRepository.findAll();
    }

    @Override
    public void clearCartSingleItem(Long cartID) {
        cartRepository.deleteById(cartID);
    }

    @Override
    public void clearCart() {
        cartRepository.deleteAll();
    }
}

package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.model.CartTable.CartTable;
import com.vinod.EcommerceApp.model.ProductTable.ProductTable;
import com.vinod.EcommerceApp.repository.CartRepository;
import com.vinod.EcommerceApp.repository.ProductTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartTableServiceImpl implements CartTableService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductTableService productTableService;

    @Override
    public void addToCart(CartTable cartItem) {
        validateCartItem(cartItem);
        handleDuplicateCartItem(cartItem);
        cartRepository.save(cartItem);
    }
    private void validateCartItem(CartTable cartItem) {
        // Get the product ID from the cart item
        String productId = cartItem.getProduct().getProductID();

        // Fetch the product from the ProductTable using its ID
        ProductTable product = productTableService.getProductById(productId);

        // Check if the product exists
        if (product == null) {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }

        // Check if the quantity is valid
        if (cartItem.getQuantity() <= 0 || cartItem.getQuantity() > product.getNoOfProductsAvailable()) {
            throw new IllegalArgumentException("Invalid quantity: Quantity must be greater than zero and less than or equal to available quantity.");
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

    @Override
    public Double subTotalCost() {
        Double subTotal = 0.0; // Use Double instead of Integer to handle decimal values

        // Retrieve all cart items
        List<CartTable> cartItems = getAllCartItems();

        // Iterate over each cart item
        for (CartTable cartItem : cartItems) {
            // Retrieve the product associated with the cart item
            ProductTable product = cartItem.getProduct();

            // Ensure product and product price are not null
            if (product != null && product.getProductPrice() != null) {
                // Remove currency symbol and parse the product price to Double
                String priceString = product.getProductPrice().replace("$", "");
                Double price = Double.parseDouble(priceString);

                // Multiply the price by the quantity and add to subtotal
                subTotal += price * cartItem.getQuantity();
            }
        }
        System.out.println("subTotal" + subTotal);

        return subTotal;
    }

}

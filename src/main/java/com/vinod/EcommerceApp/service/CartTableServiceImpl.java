
package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.model.CartTable.CartTable;
import com.vinod.EcommerceApp.model.ProductTable.ProductTable;
import com.vinod.EcommerceApp.model.User.UserEntity;
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
    public void addToCart(CartTable cartItem, UserEntity user) {
        validateCartItem(cartItem);
        cartItem.setUser(user); // Set the user property
        handleDuplicateCartItem(cartItem, user.getId()); // Pass userId to check for duplicates
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

    private void handleDuplicateCartItem(CartTable cartItem, Long userId) {
        String productIdString = cartItem.getProduct().getProductID();
        Long productId = Long.valueOf(productIdString);

        // Check if any cart item already exists for the given user and product ID
        Optional<CartTable> existingCartItem = cartRepository.findByUserIdAndProduct_ProductID(userId, String.valueOf(productId));

        // If a cart item with the same product ID already exists for the given user
        if (existingCartItem.isPresent()) {
            // For example, you can update the quantity of the existing item instead of adding a new one
            throw new IllegalArgumentException("Duplicate cart item: Product already exists in the cart.");
        }
    }

    @Override
    public List<CartTable> getAllCartItemsByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public void clearCartSingleItem(Long cartID) {
        cartRepository.deleteById(cartID);
    }

    @Override
    public void clearCartSingleItemByProductId(Long userId, String productId) {

        List<CartTable> cartItems = cartRepository.findByUserId(userId);

        // Check if any cart item contains the specified product
        for (CartTable cartItem : cartItems) {
            if (cartItem.getProduct().getProductID().equals(productId)) {
                cartRepository.deleteById(cartItem.getCartID());
            }
        }
        System.out.println("deleting using productID");
    }

    @Override
    public void clearCart(Long userId) {
        List<CartTable> cartItems = cartRepository.findByUserId(userId);
        cartRepository.deleteAll(cartItems);
    }

    @Override
    public Double subTotalCost(Long userId) {
        Double subTotal = 0.0; // Use Double instead of Integer to handle decimal values

        // Retrieve all cart items
        List<CartTable> cartItems = getAllCartItemsByUserId(userId);

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

    @Override
    public Boolean checkIfProductInCart(Long userId, String productId) {
        // Retrieve cart items for the given user
        List<CartTable> cartItems = cartRepository.findByUserId(userId);

        // Check if any cart item contains the specified product
        for (CartTable cartItem : cartItems) {
            if (cartItem.getProduct().getProductID().equals(productId)) {
                return true; // Product is in the cart
            }
        }
        return false; // Product is not in the cart
    }

}

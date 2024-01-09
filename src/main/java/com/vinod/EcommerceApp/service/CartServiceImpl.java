package com.vinod.EcommerceApp.service;


import com.vinod.EcommerceApp.model.CartTable.cartTable;
import com.vinod.EcommerceApp.model.Product;
import com.vinod.EcommerceApp.model.ProductTable.ProductTable;
import com.vinod.EcommerceApp.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public void addToCart(ProductTable product, int quantity) {
        // Create a new Cart entity and save it
        cartTable cart = new cartTable();
        cart.setProduct(product);
        cart.setQuantity(quantity);
        cartRepository.save(cart);
    }
}

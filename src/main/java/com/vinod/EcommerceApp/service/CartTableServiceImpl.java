package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.model.CartTable.CartTable;
import com.vinod.EcommerceApp.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartTableServiceImpl implements CartTableService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public void addToCart(CartTable cartItem) {
        cartRepository.save(cartItem);
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

package com.vinod.EcommerceApp.service;
import com.vinod.EcommerceApp.model.CartTable.CartTable;

import java.util.List;

public interface CartTableService {
    void addToCart(CartTable cartItem);
    List<CartTable> getAllCartItems();
    void clearCartSingleItem(Long cartID);
    void clearCart();
}


package com.vinod.EcommerceApp.service;
import com.vinod.EcommerceApp.model.CartTable.CartTable;
import com.vinod.EcommerceApp.model.User.UserEntity;

import java.util.List;

public interface CartTableService {
    void addToCart(CartTable cartItem, UserEntity user);
    List<CartTable> getAllCartItems();
    void clearCartSingleItem(Long cartID);
    void clearCart();
    Double subTotalCost();
}

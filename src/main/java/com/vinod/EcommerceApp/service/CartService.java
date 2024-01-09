package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.model.Product;
import com.vinod.EcommerceApp.model.ProductTable.ProductTable;

public interface CartService {
    void addToCart(ProductTable productTable, int quantity);
}
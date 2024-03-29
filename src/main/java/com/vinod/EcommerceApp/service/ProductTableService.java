package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.model.ProductTable.ProductTable;

import java.util.List;

public interface ProductTableService {

    ProductTable getProductById(String productId);

    void createProduct(ProductTable productTable);
    List<ProductTable> getProduct();
    void createProducts(List<ProductTable> productTable);
    void deleteProductsTable();
    List<ProductTable> searchProducts(String query);

}

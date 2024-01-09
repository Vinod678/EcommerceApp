package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.model.Product;
import com.vinod.EcommerceApp.model.ProductTable.ProductTable;

import java.util.List;

public interface ProductTableService {
    void createProductTable(ProductTable productTable);
    List<ProductTable> getProductTables();
    void createProductsTable(List<ProductTable> productTable);
    void deleteProductsTable();
}

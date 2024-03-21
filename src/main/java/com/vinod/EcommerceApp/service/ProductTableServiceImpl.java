package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.model.ProductTable.Category;
import com.vinod.EcommerceApp.model.ProductTable.ProductTable;
import com.vinod.EcommerceApp.repository.ProductTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductTableServiceImpl implements ProductTableService {

    @Autowired
    ProductTableRepository productTableRepository;

    @Override
    public ProductTable getProductById(String productId) {
        return productTableRepository.findById(Integer.valueOf(productId)).orElse(null);
    }

    @Override
    public void createProduct(ProductTable productTable) {
        //logger.info("Product Table Created : " + productTable);
        validateProductData(productTable);
//        validateCategory(productTable.getCategory()); // Validate category before saving
        duplicates(productTable);
        productTableRepository.save(productTable);
    }
    private void validateProductData(ProductTable productTable) {
        if (productTable == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (productTable.getProductName() == null || productTable.getProductName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (productTable.getProductID() == null || productTable.getProductID().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be empty");
        }
        if (productTable.getCategory() == null || !Arrays.asList(Category.values()).contains(productTable.getCategory())) {
            throw new IllegalArgumentException("Invalid Category");
        }
    }

    private void duplicates(ProductTable productTable) {
        String productId = productTable.getProductID();
        if (productTableRepository.existsById(Integer.valueOf(productId))) {
            throw new IllegalArgumentException("Product with ID " + productId + " already exists");
        }
    }




    @Override
    public void createProducts(List<ProductTable> productTables) {
        for (ProductTable productTable : productTables) {
            createProduct(productTable);
        }
    }

    @Override
    public void deleteProductsTable() {
        productTableRepository.deleteAll();
    }

    @Override
    public List<ProductTable> searchProducts(String query) {
        return productTableRepository.searchProducts(query);
    }


    @Override
    public List<ProductTable> getProduct() {
        return productTableRepository.findAll();
    }
    @Override
    public List<ProductTable> getProductByCategory(Category category) {
        return productTableRepository.findByCategory(category);
    }
}

package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.model.ProductTable.ProductTable;
import com.vinod.EcommerceApp.repository.ProductTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTableServiceImpl implements ProductTableService {

    @Autowired
    ProductTableRepository productTableRepository;

    @Override
    public void createProduct(ProductTable productTable) {
        //logger.info("Product Table Created : " + productTable);
        productTableRepository.save(productTable);
    }

    @Override
    public void createProducts(List<ProductTable> productTables) {
        for (ProductTable productTable : productTables) {
            // Perform any validation or processing as needed
            createProduct(productTable);
        }
    }

    @Override
    public void deleteProductsTable() {
        productTableRepository.deleteAll();
    }


    @Override
    public List<ProductTable> getProduct() {
        return productTableRepository.findAll();
    }
}

package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.model.Product;
import com.vinod.EcommerceApp.model.ProductTable.ProductTable;
import com.vinod.EcommerceApp.repository.ProductTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTableServiceImpl implements ProductTableService {

    @Autowired
    ProductTableRepository productTableRepository;

    //private static final Logger logger = (Logger) LoggerFactory.getLogger(ProductTableServiceImpl.class);
    @Override
    public void createProductTable(ProductTable productTable) {
        //logger.info("Product Table Created : " + productTable);
        productTableRepository.save(productTable);
    }

    @Override
    public void createProductsTable(List<ProductTable> productTables) {
        for (ProductTable productTable : productTables) {
            // Perform any validation or processing as needed
            createProductTable(productTable);
        }
    }

    @Override
    public void deleteProductsTable() {
        productTableRepository.deleteAll();
    }

    @Override
    public ProductTable getProductById(int id) {
        return productTableRepository.findById(int productID);
    }

    @Override
    public List<ProductTable> getProductTables() {
        return productTableRepository.findAll();
    }
}

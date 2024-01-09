package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.model.Product;
import com.vinod.EcommerceApp.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void createProduct(Product product) {
        logger.info("Creating product: {}", product);
        productRepository.save(product);
    }
    @Override
    public void createProducts(List<Product> products) {
        for (Product product : products) {
            // Perform any validation or processing as needed
            createProduct(product);
        }
    }


    @Override
    public void deleteProduct(Product product) {

    }

    @Override
    public void editProduct(Product product) {

    }

    @Override
    public void editProduct(String id, Product product) {
        if (productRepository.existsById(Integer.valueOf(id))) {
            product.setProdId(id);
            productRepository.save(product);
        }
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public void deleteProducts() {
        productRepository.deleteAll();
    }

    @Override
    public void sortProducts(){

    }
    @Override
    public List<Product> getAllProductsSortedByName() {
        // Retrieve all products sorted by prodName in ascending order
        return productRepository.findAll(Sort.by("prodName"));
    }

    @Override
    public List<Product> getAllProductsSortedById(){
        logger.info("Sorted List of Products by Id");
        return productRepository.findAll(Sort.by("prodId"));
    }

    @Override
    public Page<Product> getAllProductsPaginated(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

}

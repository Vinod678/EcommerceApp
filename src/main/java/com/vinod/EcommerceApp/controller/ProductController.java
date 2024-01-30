package com.vinod.EcommerceApp.controller;

import com.vinod.EcommerceApp.model.ProductTable.ProductTable;
import com.vinod.EcommerceApp.service.ProductTableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
public class ProductController {

    @Autowired
    private ProductTableService productTableService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


    @GetMapping
    public ResponseEntity<List<ProductTable>> getProducts() {
        List<ProductTable> productTables = productTableService.getProduct();
        logger.info("Products in Table " + productTables);
        return ResponseEntity.ok(productTables);
    }


    @PostMapping
    public ResponseEntity<ProductTable> createProduct(@RequestBody ProductTable productTable) {
        productTableService.createProduct(productTable);
        return ResponseEntity.ok(productTable);
    }


    @PostMapping("/createProducts")
    public ResponseEntity<?> createProducts(@RequestBody List<ProductTable> productTables) {
        try {
            logger.info("Received request to create products: {}", productTables);
            productTableService.createProducts(productTables);
            return ResponseEntity.ok("Products created successfully");
        } catch (IllegalArgumentException ex) {
            // Catch the IllegalArgumentException and return a custom error response
            logger.info("Error: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + ex.getMessage());
        }
    }


    @DeleteMapping("/deleteAllProducts")
    public ResponseEntity<String> deleteAllProducts() {
        productTableService.deleteProductsTable();
        return ResponseEntity.ok("All produts delted Successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductTable>> searchProducts(@RequestParam("query") String query) {
        List<ProductTable> searchResults = productTableService.searchProducts(query);
        return ResponseEntity.ok(searchResults);
    }


}
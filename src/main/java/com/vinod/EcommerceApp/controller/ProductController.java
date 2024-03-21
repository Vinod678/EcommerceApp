package com.vinod.EcommerceApp.controller;

import com.vinod.EcommerceApp.DTO.GetProductDTOResponse;
import com.vinod.EcommerceApp.DTO.ReviewResponseDTO;
import com.vinod.EcommerceApp.model.ProductTable.ProductTable;
import com.vinod.EcommerceApp.service.ProductTableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
public class ProductController {

    @Autowired
    private ProductTableService productTableService;
    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


    @GetMapping
    public ResponseEntity<List<ProductTable>> getProducts() {
        List<ProductTable> productTables = productTableService.getProduct();
        logger.info("Products in Table " + productTables);
        return ResponseEntity.ok(productTables);
    }
//    @GetMapping("/getProductById")
//    public ResponseEntity<ProductTable> getProductById(@RequestParam String productId) {
//        ProductTable product = productTableService.getProductById(productId);
//        if (product != null) {
//            logger.info("Product found with ID: " + productId);
//            return ResponseEntity.ok(product);
//        } else {
//            logger.warn("Product not found with ID: " + productId);
//            return ResponseEntity.notFound().build();
//        }
//    }
    @GetMapping("/getProductById")
    public ResponseEntity<GetProductDTOResponse> getProductById(@RequestParam String productId) {
        ProductTable product = productTableService.getProductById(productId);
        if (product != null) {
            logger.info("Product found with ID: " + productId);
            // Convert ProductTable to GetProductDTOResponse
            GetProductDTOResponse response = convertToDTO(product);
            return ResponseEntity.ok(response);
        } else {
            logger.warn("Product not found with ID: " + productId);
            return ResponseEntity.notFound().build();
        }
    }

    // Utility method to convert ProductTable to GetProductDTOResponse
    private GetProductDTOResponse convertToDTO(ProductTable product) {
        GetProductDTOResponse dto = new GetProductDTOResponse();
        dto.setProductID(product.getProductID());
        dto.setProductName(product.getProductName());
        dto.setProductDescription(product.getProductDescription());
        dto.setProductPrice(product.getProductPrice());
        dto.setNoOfProductsAvailable(product.getNoOfProductsAvailable());
        dto.setProductImage(product.getProductImage());
        dto.setCategory(product.getCategory().toString()); // Assuming Category is an enum

        // Fetch product reviews
        ResponseEntity<ReviewResponseDTO> reviewResponse = restTemplate.getForEntity("http://localhost:8080/product-reviews/get?productId=" + product.getProductID(), ReviewResponseDTO.class);
        ReviewResponseDTO reviewDTO = reviewResponse.getBody();
        if (reviewDTO != null) {
            dto.setAverageRating(reviewDTO.getAverageRating());
            dto.setTotalReviews(reviewDTO.getTotalReviews());
            dto.setProductReviews(reviewDTO.getReviews());
        } else {
            // Handle case when reviewDTO is null
        }

        return dto;
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
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
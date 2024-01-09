package com.vinod.EcommerceApp.controller;
import com.vinod.EcommerceApp.model.CartTable.cartTable;
import com.vinod.EcommerceApp.model.Product;
import com.vinod.EcommerceApp.model.ProductTable.ProductTable;
import com.vinod.EcommerceApp.service.CartService;
import com.vinod.EcommerceApp.service.ProductService;
import com.vinod.EcommerceApp.service.ProductTableService;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
public class Controller {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductTableService productTableService;

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);


    // ... existing mappings ...

//    @GetMapping
//    public ResponseEntity<List<Product>> getProducts() {
//        List<Product> products = productService.getAllProducts();
//        logger.info("products : " + products);
//        return ResponseEntity.ok(products);
//    }

    @GetMapping
    public ResponseEntity<List<ProductTable>> getProductsTable(){
        List<ProductTable> productTables = productTableService.getProductTables();
        logger.info("Products in Table " + productTables);
        return ResponseEntity.ok(productTables);
    }

//    @GetMapping
//    public ResponseEntity<Page<Product>> getProducts(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "prodId") String sortBy) {
//
//        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortBy));
//
//        Page<Product> products = productService.getAllProductsPaginated(pageable);
//
//        return ResponseEntity.ok(products);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PostMapping
//    public ResponseEntity<String> createProduct(@RequestBody Product product) {
//        productService.createProduct(product);
//        return ResponseEntity.ok("Product created successfully");
//    }

    @PostMapping
    public ResponseEntity<String> createProductTable(@RequestBody ProductTable productTable){
        productTableService.createProductTable(productTable);
        return ResponseEntity.ok("Product Table created Successfully");
    }
    @PostMapping("/createProducts")
    public ResponseEntity<?> createProducts(@RequestBody List<ProductTable> productTables) {
        // Handle the list of products
        logger.info("Received request to create products: {}", productTables);
        productTableService.createProductsTable(productTables);
        return ResponseEntity.ok("Products created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editProduct(@PathVariable String id, @RequestBody Product product) {
        productService.editProduct(id, product);
        return ResponseEntity.ok("Product updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        logger.info("Product " + id + " deleted Successfully");
        return ResponseEntity.ok("Product deleted successfully");
    }
    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteProducts() {
        productService.deleteProducts();
        return ResponseEntity.ok("All Products deleted successfully");
    }

    @DeleteMapping("/deleteAllProducts")
    public ResponseEntity<String> deleteAllProducts(){
        productTableService.deleteProductsTable();
        return ResponseEntity.ok("All produts delted Successfully");
    }

    @GetMapping("/sortedByName")
    public List<Product> getAllProductsSortedByName() {
        return productService.getAllProductsSortedByName();
    }
    @GetMapping("/sortedById")
    public List<Product> getAllProductsSortedById(){
        return productService.getAllProductsSortedById();
    }



    // CartController.java

    @RestController
    @RequestMapping("/cart")
    public class CartController {

        @Autowired
        private CartService cartService;

        @PostMapping("/add")
        public ResponseEntity<String> addToCart(@RequestBody cartTable cartRequest) {
            // Assume CartRequest contains productId and quantity
            ProductTable product = productService.getProductById(cartRequest.get());
            if (product != null) {
                cartService.addToCart(product, cartRequest.getQuantity());
                return ResponseEntity.ok("Product added to cart successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
            }
        }
    }




}
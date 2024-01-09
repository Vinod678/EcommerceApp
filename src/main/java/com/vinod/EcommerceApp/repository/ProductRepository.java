package com.vinod.EcommerceApp.repository;
import com.vinod.EcommerceApp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Additional custom queries if needed


}

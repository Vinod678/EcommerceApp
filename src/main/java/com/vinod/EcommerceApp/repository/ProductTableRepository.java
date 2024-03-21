package com.vinod.EcommerceApp.repository;

import com.vinod.EcommerceApp.model.ProductTable.Category;
import com.vinod.EcommerceApp.model.ProductTable.ProductTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductTableRepository extends JpaRepository<ProductTable, Integer> {

    List<ProductTable> findByProductNameContainingIgnoreCase(String query);

    @Query("SELECT p FROM ProductTable p WHERE " +
            "p.productName LIKE CONCAT('%',:query, '%')" +
            "Or p.productID LIKE CONCAT('%', :query, '%')")
    List<ProductTable> searchProducts(String query);

    List<ProductTable> findByCategory(Category category);
}

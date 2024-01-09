package com.vinod.EcommerceApp.repository;

import com.vinod.EcommerceApp.model.ProductTable.ProductTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTableRepository extends JpaRepository<ProductTable, Integer> {

}

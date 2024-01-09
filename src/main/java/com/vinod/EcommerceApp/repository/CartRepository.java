package com.vinod.EcommerceApp.repository;

import com.vinod.EcommerceApp.model.CartTable.cartTable;
import com.vinod.EcommerceApp.model.ProductTable.ProductTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<cartTable, Long> {
}


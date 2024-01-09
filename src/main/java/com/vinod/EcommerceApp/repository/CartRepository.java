package com.vinod.EcommerceApp.repository;

import com.vinod.EcommerceApp.model.CartTable.CartTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartTable, Long> {
}


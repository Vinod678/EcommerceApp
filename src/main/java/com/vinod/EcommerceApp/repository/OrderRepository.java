package com.vinod.EcommerceApp.repository;


import com.vinod.EcommerceApp.model.CartTable.CartTable;
import com.vinod.EcommerceApp.model.OrderTable.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderTable, Long> {
    List<OrderTable> findByUserId(Long userId);
}
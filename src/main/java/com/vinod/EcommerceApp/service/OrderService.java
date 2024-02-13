package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.model.OrderTable.OrderTable;
import org.hibernate.query.Order;

import java.util.List;

public interface OrderService {
    List<OrderTable> getAllOrders();
    OrderTable getOrderById(Long orderId);
    List<OrderTable> getOrdersByUserId(Long userId);
    OrderTable createOrder(OrderTable order);
    //When order placed through cart checkOut
    List<OrderTable> createOrders(List<OrderTable> orders);
    void deleteOrder(Long orderId);
}
package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.model.OrderTable.OrderTable;
import com.vinod.EcommerceApp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<OrderTable> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public OrderTable getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public List<OrderTable> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public OrderTable createOrder(OrderTable order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

}
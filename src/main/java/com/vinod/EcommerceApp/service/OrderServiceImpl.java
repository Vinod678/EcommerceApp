package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.model.OrderTable.OrderTable;
import com.vinod.EcommerceApp.model.ProductTable.ProductTable;
import com.vinod.EcommerceApp.repository.OrderRepository;
import com.vinod.EcommerceApp.repository.ProductTableRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductTableRepository productTableRepository;

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
    @Transactional
    public OrderTable createOrder(OrderTable order) {
        // Decrease product quantity
        decreaseProductQuantity(order.getProductId(), order.getQuantity());

        // Save the order
        return orderRepository.save(order);
    }

    // Method to decrease product quantity
    private void decreaseProductQuantity(String productId, int quantity) {
        ProductTable product = productTableRepository.findById(Integer.valueOf(productId)).orElse(null);
        if (product != null) {
            int availableQuantity = product.getNoOfProductsAvailable();
            if (availableQuantity >= quantity) {
                product.setNoOfProductsAvailable(availableQuantity - quantity);
                productTableRepository.save(product);
            } else {
                throw new IllegalArgumentException("Insufficient quantity available for product: " + productId);
            }
        } else {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

}
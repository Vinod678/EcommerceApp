package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.model.OrderTable.OrderTable;
import com.vinod.EcommerceApp.model.ProductTable.ProductTable;
import com.vinod.EcommerceApp.repository.OrderRepository;
import com.vinod.EcommerceApp.repository.ProductTableRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Override
    @Transactional
    public List<OrderTable> createOrders(List<OrderTable> orders) {
        List<OrderTable> createdOrders = new ArrayList<>();
        for (OrderTable order : orders) {
            // Decrease product quantity for each order
            decreaseProductQuantity(order.getProductId(), order.getQuantity());
            // Save the order
            createdOrders.add(orderRepository.save(order));
        }
        return createdOrders;
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
    @Override
    public boolean cancelOrder(Long orderId) {
        //Retrieve the order from the database
        Optional<OrderTable> optionalOrder = orderRepository.findById(orderId);

        // Check if the order exists
        if (optionalOrder.isPresent()) {
            OrderTable order = optionalOrder.get();
        // Update the status of the order to "Canceled"
            order.setStatus("Canceled");
            orderRepository.save(order);
            return true;
        } else {
            return false;
        }
    }

}
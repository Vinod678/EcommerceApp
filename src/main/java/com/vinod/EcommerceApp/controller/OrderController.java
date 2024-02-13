package com.vinod.EcommerceApp.controller;

import com.vinod.EcommerceApp.model.CartTable.CartTable;
import com.vinod.EcommerceApp.model.OrderTable.OrderTable;
import com.vinod.EcommerceApp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<String> createOrder(@RequestBody OrderTable orderTable) {
        orderService.createOrder(orderTable);
        return ResponseEntity.ok("Order created successfully");
    }

    //When order placed through cart checkOut
    @PostMapping("/createOrders")
    public ResponseEntity<String> createOrders(@RequestBody List<OrderTable> orders) {
        orderService.createOrders(orders);
        return ResponseEntity.ok("Orders created successfully");
}


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderTable> getOrderById(@PathVariable Long orderId) {
        OrderTable order = orderService.getOrderById(orderId);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<OrderTable>> getAllOrders(){
        List<OrderTable> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }


    @GetMapping("/getOrdersByUserId")
    public ResponseEntity<List<OrderTable>> getOrdersByUserId(@RequestParam Long userId) {
        List<OrderTable> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
    @DeleteMapping("/cancelOrder/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        // Logic to update the order status to "Canceled" in the database
        boolean success = orderService.cancelOrder(orderId);
        if (success) {
            return ResponseEntity.ok("Order canceled successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to cancel order");
        }
    }


}
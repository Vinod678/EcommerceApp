package com.vinod.EcommerceApp.model.PaymentDetailsTable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class paymentDetailsTable {
    @Id
    private String OrderId;
    private String CustomerId;

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }
}

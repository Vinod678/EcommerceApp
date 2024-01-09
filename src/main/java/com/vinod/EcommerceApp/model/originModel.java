package com.vinod.EcommerceApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class originModel {
    @Id
    private String productTable;
    private String cartTable;
    private String orderTable;
    private String customerTable;
    private String paymentReceiptsTable;

    public String getCartTable() {
        return cartTable;
    }

    public void setCartTable(String cartTable) {
        this.cartTable = cartTable;
    }

    public String getProductTable() {
        return productTable;
    }

    public void setProductTable(String productTable) {
        this.productTable = productTable;
    }


    public String getOrderTable() {
        return orderTable;
    }

    public void setOrderTable(String orderTable) {
        this.orderTable = orderTable;
    }

    public String getCustomerTable() {
        return customerTable;
    }

    public void setCustomerTable(String customerTable) {
        this.customerTable = customerTable;
    }

    public String getPaymentReceiptsTable() {
        return paymentReceiptsTable;
    }

    public void setPaymentReceiptsTable(String paymentReceiptsTable) {
        this.paymentReceiptsTable = paymentReceiptsTable;
    }



}

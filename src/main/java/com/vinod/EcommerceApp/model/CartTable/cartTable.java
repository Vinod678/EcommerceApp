package com.vinod.EcommerceApp.model.CartTable;

import com.vinod.EcommerceApp.model.Product;
import com.vinod.EcommerceApp.model.ProductTable.ProductTable;
import jakarta.persistence.*;

@Entity
@Table(name = "cartTable")
public class cartTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "productID")
    private ProductTable productTable;

    private int quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductTable getProduct() {
        return productTable;
    }

    public void setProduct(ProductTable productTable) {
        this.productTable = productTable;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
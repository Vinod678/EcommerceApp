package com.vinod.EcommerceApp.model.CartTable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.vinod.EcommerceApp.model.ProductTable.ProductTable;
import jakarta.persistence.*;


@Entity
@Table(name = "cartTable")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cartID")
public class CartTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartID")
    private Long cartID;

    @ManyToOne
    @JoinColumn(name = "productID", nullable = false)
    private ProductTable product;

    @Column(name = "quantity")
    private int quantity;

    public Long getCartID() {
        return cartID;
    }

    public void setCartID(Long cartID) {
        this.cartID = cartID;
    }

    public ProductTable getProduct() {
        return product;
    }

    public void setProduct(ProductTable product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

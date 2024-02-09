package com.vinod.EcommerceApp.model.CartTable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.vinod.EcommerceApp.model.ProductTable.ProductTable;
import com.vinod.EcommerceApp.model.User.UserEntity;
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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    // Add Many-to-One mapping with UserEntity
    // Remove @JsonManagedReference annotation if present
    @JsonIgnore // Exclude this field from JSON serialization
    private UserEntity user;

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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
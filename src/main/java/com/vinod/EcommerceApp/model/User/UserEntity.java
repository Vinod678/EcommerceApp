package com.vinod.EcommerceApp.model.User;

import com.vinod.EcommerceApp.model.CartTable.CartTable;
import com.vinod.EcommerceApp.model.ProductTable.ProductReview;
import jakarta.persistence.*;

import java.util.Set;
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId") // Make sure the column name matches your database schema
    private Long id; // Change the property name to 'id'

    private String userEmail;
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfileEntity userProfile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<CartTable> cartEntries;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<ProductReview> productReviews;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<CartTable> getCartEntries() {
        return cartEntries;
    }

    public void setCartEntries(Set<CartTable> cartEntries) {
        this.cartEntries = cartEntries;
    }

    public Set<ProductReview> getProductReviews() {
        return productReviews;
    }

    public void setProductReviews(Set<ProductReview> productReviews) {
        this.productReviews = productReviews;
    }
}
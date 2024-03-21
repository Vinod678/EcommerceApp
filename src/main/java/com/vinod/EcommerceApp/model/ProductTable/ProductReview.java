package com.vinod.EcommerceApp.model.ProductTable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.vinod.EcommerceApp.model.User.UserEntity;
import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "ProductReview")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "reviewID")
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewID")
    private Long reviewID;

    @ManyToOne
    @JoinColumn(name = "productID", nullable = false)
    private ProductTable product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    // Add Many-to-One mapping with UserEntity
    // Remove @JsonManagedReference annotation if present
    @JsonIgnore // Exclude this field from JSON serialization
    private UserEntity user;

    private int rating;
    private String reviewText;

    @Column(name = "review_date")
    private Date reviewDate;

    public Long getReviewID() {
        return reviewID;
    }

    public void setReviewID(Long reviewID) {
        this.reviewID = reviewID;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }
}
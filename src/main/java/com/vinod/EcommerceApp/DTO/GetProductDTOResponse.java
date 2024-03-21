package com.vinod.EcommerceApp.DTO;

import java.util.List;

public class GetProductDTOResponse {
    private String productID;
    private String productName;
    private String productDescription;
    private String productPrice;
    private int noOfProductsAvailable;
    private String productImage;
    private String category;
    private double averageRating;
    private int totalReviews;
    private List<ReviewDTO> productReviews;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public int getNoOfProductsAvailable() {
        return noOfProductsAvailable;
    }

    public void setNoOfProductsAvailable(int noOfProductsAvailable) {
        this.noOfProductsAvailable = noOfProductsAvailable;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    public List<ReviewDTO> getProductReviews() {
        return productReviews;
    }

    public void setProductReviews(List<ReviewDTO> productReviews) {
        this.productReviews = productReviews;
    }
}

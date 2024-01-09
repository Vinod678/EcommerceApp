package com.vinod.EcommerceApp.model.ProductTable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProductTable {
    @Id
    @Column(name="productID")
    private String productID;
    private String productName;
    private String productDescription;
    private String productPrice;
    private String noOfProductsAvaiable;
    private String productImage;

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

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

    public String getNoOfProductsAvaiable() {
        return noOfProductsAvaiable;
    }

    public void setNoOfProductsAvaiable(String noOfProductsAvaiable) {
        this.noOfProductsAvaiable = noOfProductsAvaiable;
    }
}

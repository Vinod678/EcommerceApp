package com.vinod.EcommerceApp.model.ProductTable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vinod.EcommerceApp.model.CartTable.CartTable;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class ProductTable {
    @Id
    @Column(name = "productID")
    private String productID;
    private String productName;
    private String productDescription;
    private String productPrice;
    private int noOfProductsAvailable;
    private String productImage;


    @ManyToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<CartTable> carts;


    public Set<CartTable> getCarts() {
        return carts;
    }

    public void setCarts(Set<CartTable> carts) {
        this.carts = carts;
    }

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

    public int getNoOfProductsAvailable() {
        return noOfProductsAvailable;
    }

    public void setNoOfProductsAvailable(int noOfProductsAvailable) {
        this.noOfProductsAvailable = noOfProductsAvailable;
    }
}

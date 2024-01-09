package com.vinod.EcommerceApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private String prodId;
    private String prodName;
    private String prodCost;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdCost() {
        return prodCost;
    }

    public void setProdCost(String prodCost) {
        this.prodCost = prodCost;
    }
    @Override
    public String toString() {
        return "{" +
                "prodId=" + prodId +
                ", prodName='" + prodName + '\'' +
                ", prodCost='" + prodCost + '\'' +
                '}';
    }
}

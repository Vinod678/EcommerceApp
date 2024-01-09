package com.vinod.EcommerceApp.repository;

import com.vinod.EcommerceApp.model.PriceTable.priceTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PriceTableRepository extends JpaRepository<priceTable,Integer> {
    @Query(value = "INSERT INTO price_table (productID, product_price) SELECT productID, product_price FROM product_table", nativeQuery = true)
    void copyDataFromProductTable();
}

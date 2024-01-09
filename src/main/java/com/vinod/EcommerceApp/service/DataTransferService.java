package com.vinod.EcommerceApp.service;

import com.vinod.EcommerceApp.repository.PriceTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataTransferService {
    @Autowired
    private PriceTableRepository priceTableRepository;

    public void transferData(){
        priceTableRepository.copyDataFromProductTable();
    }
}

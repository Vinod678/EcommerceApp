package com.vinod.EcommerceApp;

import com.vinod.EcommerceApp.controller.ProductController;
import com.vinod.EcommerceApp.model.ProductTable.ProductTable;
import com.vinod.EcommerceApp.service.ProductTableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    @Mock
    private ProductTableService productTableService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetProducts() {
        // Mocking the getProduct method of the service
        List<ProductTable> productList = Arrays.asList(new ProductTable(), new ProductTable());
        when(productTableService.getProduct()).thenReturn(productList);

        // Invoking the controller method
        ResponseEntity<List<ProductTable>> responseEntity = productController.getProducts();

        // Asserting the response
        assertEquals(productList, responseEntity.getBody());
    }


}

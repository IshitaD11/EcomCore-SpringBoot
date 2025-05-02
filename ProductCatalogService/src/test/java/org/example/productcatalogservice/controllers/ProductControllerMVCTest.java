package org.example.productcatalogservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.services.IProductService;
import org.example.productcatalogservice.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void TestGetProducts_RunsSuccessfully() throws Exception {
        Product product1 = new Product();
        product1.setTitle("Iphone16");
        product1.setId(1L);

        Product product2 = new Product();
        product2.setTitle("OnePlus Nord");
        product2.setId(2L);

        List<Product> productList = new ArrayList<>(List.of(product1,product2));

        when(productService.getAllProducts()).thenReturn(productList);

        List<ProductDto> expectedOutputProductDtoList = productList.stream()
                        .map( p -> {ProductDto productDto = new ProductDto();
                        productDto.setTitle(p.getTitle());
                        productDto.setId(p.getId());
                        return productDto;}).toList();

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedOutputProductDtoList)));


    }

}
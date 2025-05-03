package org.example.productcatalogservice.controllers;

import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.services.IProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest  // loads whole application including db, not able to pick db information from environment variable
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockitoBean
    private IProductService productService;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Test
    public void testGetProductById_WithValidProductId_RunSuccessfully() {
        Long productId = 10L;
        Product product = new Product();
        product.setId(productId);
        product.setTitle("Iphone16");
        when(productService.getProductById(productId))
                .thenReturn(product);

        ResponseEntity<ProductDto> response = productController.getProductById(productId);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(productId, response.getBody().getId());
        assertEquals("Iphone16", response.getBody().getTitle());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("passing id -1 leads to product not found exception")
    public void testGetProductById_WithInvalidProductId_ResultsException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> productController.getProductById(-1L));
        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    public void testGetProductById_WhenProductServiceThrowsException(){
        when(productService.getProductById(any(Long.class)))
                .thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> productController.getProductById(10L));
    }

    @Test
    public void Test_GetProductById_ServiceCalledWithExpectedArguments_RunSuccessfully() {
        //Arrange
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setTitle("Nokia");
        when(productService.getProductById(any(Long.class)))
                .thenReturn(product);
        //Act
        productController.getProductById(productId);

        //Assert
        verify(productService).getProductById(idCaptor.capture());
        assertEquals(productId,idCaptor.getValue());
    }
}
package org.example.productcatalogservice.controllers;

import org.example.productcatalogservice.dtos.CategoryDto;
import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.repositories.CategoryRepository;
import org.example.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ProductProductDTOUtil pDtoUtil;

    @GetMapping("/products/{productId}/{userId}")
    public ResponseEntity<ProductDto> getProductByUserRoles(@PathVariable Long productId, @PathVariable Long userId) {
        try {
            Product product = productService.getProductByUserRoles(productId,userId);
            if (product == null) {
                throw new RuntimeException("Product not found");
            }
            return new ResponseEntity<>(pDtoUtil.productDtoFromProduct(product), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts() {
        try{
            System.out.println("Inside ProductController");
            List<Product> products = productService.getAllProducts();
            if(products != null && !products.isEmpty()){
                List<ProductDto> productDtos = products.stream().map(pDtoUtil::productDtoFromProduct).toList();
                return new ResponseEntity<>(productDtos, HttpStatus.OK);
            }
            else
                throw new RuntimeException("No products found");
        }catch (Exception e){
            throw e;
        }
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        try {
            Product product = productService.getProductById(productId);
            if (product == null) {
                throw new RuntimeException("Product not found");
            }
            return new ResponseEntity<>(pDtoUtil.productDtoFromProduct(product), HttpStatus.OK);
        }catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productdto) {
        try{
            System.out.println(productdto.getVisibility().toString());
            Product product = productService.createProduct(pDtoUtil.productFromProductDto(productdto));
            if (product == null) {
                throw new RuntimeException("Product not found");
            }
            return new ResponseEntity<>(pDtoUtil.productDtoFromProduct(product), HttpStatus.CREATED);
        }catch (Exception e){
            throw e;
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> replaceProduct(@PathVariable("id") Long id, @RequestBody ProductDto productdto) {
        try{
            Product product = productService.replaceProduct(id,pDtoUtil.productFromProductDto(productdto));
            if (product == null) {
                throw new RuntimeException("Product not found");
            }
            return new ResponseEntity<>(pDtoUtil.productDtoFromProduct(product), HttpStatus.OK);
        }catch (Exception e){
            throw e;
        }
    }

}

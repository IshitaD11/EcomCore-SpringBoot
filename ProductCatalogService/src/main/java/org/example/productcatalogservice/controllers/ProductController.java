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
    private CategoryRepository categoryRepository;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts() {
        try{
            List<Product> products = productService.getAllProducts();
            if(products != null && !products.isEmpty()){
                List<ProductDto> productDtos = products.stream().map(this::productDtoFromProduct).toList();
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
            if (productId == null || productId <= 0 || productId > 20) {
                throw new RuntimeException("Product not found");
            }
            Product product = productService.getProductById(productId);
            if (product == null) {
                return null;
            }
            return new ResponseEntity<>(productDtoFromProduct(product), HttpStatus.OK);
        }catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody ProductDto productdto) {
        try{
//            if(productdto.getId()!= null && productdto.getId()<=20)
//                throw new RuntimeException( "Product id must be greater than 20" );
            Product product = productService.createProduct(productFromProductDto(productdto));
            if (product == null) {
                throw new RuntimeException("Product not found");
            }
//            return new ResponseEntity<>(productDtoFromProduct(product), HttpStatus.CREATED);
            return productDtoFromProduct(product);
        }catch (Exception e){
            throw e;
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> replaceProduct(@PathVariable("id") Long id, @RequestBody ProductDto productdto) {
        try{
            Product product = productService.replaceProduct(id,productFromProductDto(productdto));
            if (product == null) {
                throw new RuntimeException("Product not found");
            }
            return new ResponseEntity<>(productDtoFromProduct(product), HttpStatus.OK);
        }catch (Exception e){
            throw e;
        }
    }

    private Product productFromProductDto(ProductDto productdto) {
        Product product = new Product();
        product.setId(productdto.getId());
        product.setTitle(productdto.getTitle());
        product.setDescription(productdto.getDescription());
        product.setPrice(productdto.getPrice());
        product.setImgUrl(productdto.getImgUrl());
        if(productdto.getCategory() != null) {
            Category category = new Category();
            if(productdto.getCategory().getId() != null) {
                category = categoryRepository.findById(productdto.getCategory().getId()).orElse(null);
            }
            if(category != null){
                product.setCategory(category);
            }
            else{
                category = new Category();
//                category.setId(productdto.getCategory().getId());
                category.setCategoryDescription(productdto.getCategory().getCategoryDescription());
                category.setCategoryName(productdto.getCategory().getCategoryName());
                category = categoryRepository.save(category);
                product.setCategory(category);
            }
        }
        return product;
    }

    private ProductDto productDtoFromProduct(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImgUrl(product.getImgUrl());
        if(product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setCategoryDescription(product.getCategory().getCategoryDescription());
            categoryDto.setCategoryName(product.getCategory().getCategoryName());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }
}

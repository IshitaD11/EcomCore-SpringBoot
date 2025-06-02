package org.example.productcatalogservice.controllers;

import org.example.productcatalogservice.dtos.CategoryDto;
import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductProductDTOUtil {

    Product productFromProductDto(ProductDto productdto) {
        Product product = new Product();
        product.setId(productdto.getId());
        product.setTitle(productdto.getTitle());
        product.setDescription(productdto.getDescription());
        product.setPrice(productdto.getPrice());
        product.setImgUrl(productdto.getImgUrl());
        if(productdto.getCategory() != null) {
            Category category = new Category();
            category.setId(productdto.getCategory().getId());
            category.setCategoryDescription(productdto.getCategory().getCategoryDescription());
            category.setCategoryName(productdto.getCategory().getCategoryName());
            product.setCategory(category);
        }
        return product;
    }

    ProductDto productDtoFromProduct(Product product) {
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

package org.example.productcatalogservice.services;

import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IProductService {

    public List<Product> getAllProducts() ;

    public Product getProductById(Long productId) ;

    public Product createProduct(Product product) ;

    public Product replaceProduct(Long productId, Product product) ;

    public Product getProductByUserRoles(Long userId, Long productId) ;
}

package org.example.productcatalogservice.services;

import org.example.productcatalogservice.dtos.FakeStoreProductDto;
import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public List<Product> getProducts() {
        return List.of();
    }

    @Override
    public Product getProductById(Long productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> responseEntity =
                restTemplate.getForEntity("https://fakestoreapi.com/products/{productId}",FakeStoreProductDto.class,productId);
        FakeStoreProductDto fakeStoreProductDto = responseEntity.getBody();
        if(responseEntity.getStatusCode().equals(HttpStatus.valueOf(200)) && fakeStoreProductDto!=null)
            return getProductFromFakeStoreProductDto(fakeStoreProductDto);
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    private Product getProductFromFakeStoreProductDto(FakeStoreProductDto fakeStoreProductDto){
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setImgUrl(fakeStoreProductDto.getImage());
        if(fakeStoreProductDto.getCategory() != null){
            Category category = new Category();
            category.setCategoryName(fakeStoreProductDto.getCategory());
            product.setCategory(category);
        }
        return product;
    }
}

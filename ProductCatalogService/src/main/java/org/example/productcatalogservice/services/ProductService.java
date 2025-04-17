package org.example.productcatalogservice.services;

import org.example.productcatalogservice.dtos.FakeStoreProductDto;
import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ProductService implements IProductService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public List<Product> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> responseEntity = restTemplate.getForEntity("https://fakestoreapi.com/products",FakeStoreProductDto[].class);
        FakeStoreProductDto[] fakeStoreProductDtoArr = responseEntity.getBody();
        if(responseEntity.getStatusCode().equals(HttpStatus.valueOf(200)) && fakeStoreProductDtoArr != null && fakeStoreProductDtoArr.length > 0) {
            return Arrays.stream(fakeStoreProductDtoArr).map(this::getProductFromFakeStoreProductDto).toList();
        }
        return null;
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

    // Need to do
    @Override
    public Product createProduct(Product product) {
        ResponseEntity<FakeStoreProductDto> responseEntity =
                requestForEntity("https://fakestoreapi.com/products",
                        HttpMethod.POST,
                        FakeStoreProductDtoFromProduct(product),
                        FakeStoreProductDto.class);
        if(responseEntity.getStatusCode().equals(HttpStatus.valueOf(200)) && responseEntity.getBody()!=null){
            return getProductFromFakeStoreProductDto(responseEntity.getBody());
        }
        return null;
    }

    @Override
    public Product replaceProduct(Long productId, Product product) {
        ResponseEntity<FakeStoreProductDto> responseEntity =
                requestForEntity("https://fakestoreapi.com/products/{productId}",
                                    HttpMethod.PUT,
                                    FakeStoreProductDtoFromProduct(product),
                                    FakeStoreProductDto.class,
                                    productId);
        if(responseEntity.getStatusCode().equals(HttpStatus.valueOf(200)) && responseEntity.getBody()!=null){
            return getProductFromFakeStoreProductDto(responseEntity.getBody());
        }
        return null;
    }

    private FakeStoreProductDto FakeStoreProductDtoFromProduct(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImgUrl());
        if(product.getCategory() != null){
            fakeStoreProductDto.setCategory(product.getCategory().getCategoryName());
        }
        return fakeStoreProductDto;
    }

    public <T> ResponseEntity<T> requestForEntity(String url,
                                               HttpMethod httpMethod,
                                               @Nullable Object request,
                                               Class<T> responseType,
                                               Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
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

package org.example.productcatalogservice.clients;

import org.example.productcatalogservice.dtos.FakeStoreProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class FakeStoreApiClient {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;


    public FakeStoreProductDto getProductById(Long productId) {
        ResponseEntity<FakeStoreProductDto> responseEntity =
                requestForEntity("https://fakestoreapi.com/products/{productId}",
                        HttpMethod.GET,
                        null,
                        FakeStoreProductDto.class,
                        productId);
        FakeStoreProductDto fakeStoreProductDto = responseEntity.getBody();
        if(responseEntity.getStatusCode().equals(HttpStatus.valueOf(200)) && fakeStoreProductDto != null)
            return fakeStoreProductDto;
        return null;
    }

    public List<FakeStoreProductDto> getAllProducts() {
        ResponseEntity<FakeStoreProductDto[]> responseEntity =
                requestForEntity("https://fakestoreapi.com/products",
                        HttpMethod.GET,
                        null,
                        FakeStoreProductDto[].class);
        FakeStoreProductDto[] fakeStoreProductDtoArr = responseEntity.getBody();
        if(responseEntity.getStatusCode().equals(HttpStatus.valueOf(200)) && fakeStoreProductDtoArr != null && fakeStoreProductDtoArr.length > 0) {
            return Arrays.stream(fakeStoreProductDtoArr).toList();
        }
        return null;
    }


    public FakeStoreProductDto createProduct(FakeStoreProductDto inputFakeStoreProductDto) {
        ResponseEntity<FakeStoreProductDto> responseEntity =
                requestForEntity("https://fakestoreapi.com/products",
                        HttpMethod.POST,
                        inputFakeStoreProductDto,
                        FakeStoreProductDto.class);
        if(responseEntity.getStatusCode().equals(HttpStatus.valueOf(200)) && responseEntity.getBody()!=null){
            return responseEntity.getBody();
        }
        return null;
    }

    public FakeStoreProductDto replaceProduct(Long productId, FakeStoreProductDto inputFakeStoreProductDto) {
        ResponseEntity<FakeStoreProductDto> responseEntity =
                requestForEntity("https://fakestoreapi.com/products/{productId}",
                        HttpMethod.PUT,
                        inputFakeStoreProductDto,
                        FakeStoreProductDto.class,
                        productId);
        if(responseEntity.getStatusCode().equals(HttpStatus.valueOf(200)) && responseEntity.getBody()!=null){
            return responseEntity.getBody();
        }
        return null;
    }


    private <T> ResponseEntity<T> requestForEntity(String url,
                                                  HttpMethod httpMethod,
                                                  @Nullable Object request,
                                                  Class<T> responseType,
                                                  Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }
}

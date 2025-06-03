package org.example.productcatalogservice.controllers;

import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.dtos.SearchProductRequestDto;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.services.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ISearchService searchService;

    @Autowired
    private ProductProductDTOUtil pDtoUtil;

    @PostMapping
    public ResponseEntity<Page<ProductDto>> searchProducts(@RequestBody SearchProductRequestDto requestDto){
        try{
            Page<Product> products = searchService.searchProduct(requestDto.getQuery(),requestDto.getPageNo(), requestDto.getPageSize(),requestDto.getSortParamList());
            Page<ProductDto> productDtos = products.map(pDtoUtil::productDtoFromProduct);
            return new ResponseEntity<>(productDtos, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}

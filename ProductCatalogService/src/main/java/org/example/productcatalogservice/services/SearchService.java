package org.example.productcatalogservice.services;

import org.example.productcatalogservice.dtos.SortParam;
import org.example.productcatalogservice.dtos.SortType;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService implements ISearchService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> searchProduct(String query, Integer pageNo, Integer pageSize, List<SortParam> sortParamList) {

        Sort sort = null;

        if(sortParamList != null) {
            sort = Sort.by(sortParamList.get(0).getAttribute());
            if(sortParamList.get(0).getSortType().equals(SortType.DESC))
                sort = sort.descending();
        }

        for(int i=0;i< sortParamList.size();i++){
            sort = sort.and(Sort.by(sortParamList.get(i).getAttribute()));
            if(sortParamList.get(i).getSortType().equals(SortType.DESC))
                sort = sort.descending();
        }

        return productRepository.findProductByTitleEquals(query, PageRequest.of(pageNo,pageSize,sort));

    }
}

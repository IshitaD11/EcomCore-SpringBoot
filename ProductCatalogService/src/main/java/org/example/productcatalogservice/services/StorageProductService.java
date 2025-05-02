package org.example.productcatalogservice.services;

import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.repositories.CategoryRepository;
import org.example.productcatalogservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class StorageProductService implements IProductService{

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        if(product.getId()!=null)
            product.setId(null);
        if(product.getCategory()!=null){
            Category category = getCategoryFromRepo(product);
            if(category!=null){
                product.setCategory(category);
            }else{
                product.getCategory().setId(null);
            }
        }
        return productRepository.save(product);
    }

    @Override
    public Product replaceProduct(Long productId, Product product) {
        Product existingProduct  = productRepository.findById(productId).orElse(null);
        if(existingProduct  == null) {return null;}

        existingProduct.setTitle(product.getTitle());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setImgUrl(product.getImgUrl());

        Category incomingCategory = product.getCategory();

        if(incomingCategory!=null){
            Category category = getCategoryFromRepo(product);

            if(category!=null){
                existingProduct.setCategory(category);
            }else{
                incomingCategory.setId(null);
                Category savedCategory = categoryRepository.save(incomingCategory);
                existingProduct.setCategory(savedCategory);
            }
        }else{
            existingProduct.setCategory(null);
        }
        return productRepository.save(existingProduct);
    }

    private Category getCategoryFromRepo(Product product) {
        Category category = null;
        if(product.getCategory() != null) {
            if (product.getCategory().getId() != null) {
                category = categoryRepository.findById(product.getCategory().getId()).orElse(null);
            } else if (product.getCategory().getCategoryName() != null) {
                category = categoryRepository.findByCategoryName(product.getCategory().getCategoryName()).orElse(null);
            }
        }
        return category;
    }
}

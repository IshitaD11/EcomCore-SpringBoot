package org.example.productcatalogservice.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Category extends BaseModel{
    private String categoryName;
    private String categoryDescription;
    private List<Product> productList;
}

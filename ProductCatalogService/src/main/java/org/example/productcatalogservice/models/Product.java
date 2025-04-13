package org.example.productcatalogservice.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product extends BaseModel{
    private String title;
    private String description;
    private String imgUrl;
    private Double price;
    private Category category;
    private boolean isPrimeSpecific;
}

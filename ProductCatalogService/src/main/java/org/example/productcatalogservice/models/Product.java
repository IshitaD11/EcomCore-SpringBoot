package org.example.productcatalogservice.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product extends BaseModel{
    private String title;
    private String description;
    private String imgUrl;
    private Double price;
    private Category category;

    public void setTitle(String title) {
        this.title = title;
    }
}

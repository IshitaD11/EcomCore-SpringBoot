package org.example.productcatalogservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Category extends BaseModel{
    private String categoryName;
    private String categoryDescription;

    // category is the field name in Product class, to tell JPA the other side of the relationship
    @OneToMany(mappedBy = "category")
    private List<Product> productList;
}

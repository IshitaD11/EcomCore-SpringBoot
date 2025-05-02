package org.example.productcatalogservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jdk.jfr.Enabled;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product extends BaseModel{
    private String title;
    private String description;
    private String imgUrl;
    private Double price;

    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;

    private boolean isPrimeSpecific;
}

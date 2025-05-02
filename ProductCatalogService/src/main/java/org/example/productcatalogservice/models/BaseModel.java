package org.example.productcatalogservice.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@MappedSuperclass
public abstract class BaseModel {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private Date createdAt;

    private Date updatedAt;

    private State state;

}

package org.example.productcatalogservice.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public abstract class BaseModel {

    private Long id;

    private Date createdAt;

    private Date updatedAt;

    private State state;

}

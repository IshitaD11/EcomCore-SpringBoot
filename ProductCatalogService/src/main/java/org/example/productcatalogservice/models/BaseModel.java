package org.example.productcatalogservice.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class BaseModel {

    private Long id;

    private Date createdAt;

    private Date updatedAt;

    private State state;

    public void setId(Long id) {
        this.id = id;
    }
}

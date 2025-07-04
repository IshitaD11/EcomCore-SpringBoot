package org.example.orderservice.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import jakarta.persistence.*;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private Date createdAt;

    private Date updatedAt;

    private State state;
}

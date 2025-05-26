package org.example.userauthenticationservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Session extends BaseModel {
    private SessionState sessionState;
    private String token;
    private Date expiryDate;

    @ManyToOne
    private User user;
}

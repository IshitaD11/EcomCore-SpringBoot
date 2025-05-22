package org.example.userauthenticationservice.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonDeserialize(as = User.class)
public class User extends BaseModel{
    private String email;
    private String password;
    private Role role;
}

package org.example.userauthenticationservice.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@JsonDeserialize(as = Role.class)
public class Role extends BaseModel {
    @Enumerated(value = EnumType.STRING)
    private RoleTypeName roleTypeName;

    public Role(){
        this.roleTypeName = null;
    }

    public Role(RoleTypeName roleTypeName) {
        this.roleTypeName = roleTypeName;
    }
}

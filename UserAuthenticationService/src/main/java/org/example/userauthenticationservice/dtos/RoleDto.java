package org.example.userauthenticationservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.userauthenticationservice.models.RoleTypeName;

@Getter
@Setter
public class RoleDto {
    private RoleTypeName roleTypeName;

    public RoleDto(RoleTypeName roleTypeName) {
        this.roleTypeName = roleTypeName;
    }
}

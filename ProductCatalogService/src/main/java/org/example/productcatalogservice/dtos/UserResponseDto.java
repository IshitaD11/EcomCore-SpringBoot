package org.example.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class UserResponseDto {
    private String email;
    private ResponseStatus responseStatus;
    private List<RoleDto> roles;
}

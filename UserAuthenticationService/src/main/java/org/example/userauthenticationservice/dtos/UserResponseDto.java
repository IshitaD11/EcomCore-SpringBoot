package org.example.userauthenticationservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponseDto {
    private String email;
    private String name;
    private String phoneNo;
    private ResponseStatus responseStatus;
    private List<RoleDto> roles;
}

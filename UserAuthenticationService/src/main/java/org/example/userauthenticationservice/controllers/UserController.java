package org.example.userauthenticationservice.controllers;

import org.example.userauthenticationservice.dtos.ResponseStatus;
import org.example.userauthenticationservice.dtos.RoleDto;
import org.example.userauthenticationservice.dtos.UserResponseDto;
import org.example.userauthenticationservice.models.Role;
import org.example.userauthenticationservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.example.userauthenticationservice.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id) {
        UserResponseDto userResponseDto = new UserResponseDto();
        User user = userService.getUserDetails(id);
        List<RoleDto> roleDtos = user.getRoles().stream().map(role -> new RoleDto(role.getRoleTypeName())).collect(Collectors.toList());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setName(user.getName());
        userResponseDto.setPhoneNo(user.getPhoneNumber());
        userResponseDto.setRoles(roleDtos);
        return ResponseEntity.ok(userResponseDto);
    }
}

package org.example.orderservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponseDto {
    private String email;
    private String name;
    private String phoneNo;
    private ResponseStatus responseStatus;
}

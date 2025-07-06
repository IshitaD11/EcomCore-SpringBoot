package org.example.orderservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private Double totalAmount;
    private String status;
    private List<OrderItemResponseDto> items;
    private String paymentLink;
}

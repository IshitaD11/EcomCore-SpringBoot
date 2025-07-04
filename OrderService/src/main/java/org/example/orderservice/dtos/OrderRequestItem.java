package org.example.orderservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestItem {
    private Long productId;
    private int quantity;
}

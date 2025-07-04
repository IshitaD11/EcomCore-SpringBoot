package org.example.orderservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentLinkUpdateRequest {
    private String paymentLink;
}
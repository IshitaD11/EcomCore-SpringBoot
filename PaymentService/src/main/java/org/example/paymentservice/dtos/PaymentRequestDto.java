package org.example.paymentservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDto {

    private Long amount;
    private String ref_id;
    private String description;
    private String phoneNo;
    private String userName;
    private String email;
}

package org.example.paymentservice.controllers;

import org.example.paymentservice.dtos.PaymentRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.example.paymentservice.services.IPaymemtService;

import java.util.Locale;

@RestController
public class PaymentController {

    @Autowired
    private IPaymemtService paymemtService;

    @PostMapping("/payment")
    public ResponseEntity<String> initiatePayment(@RequestBody PaymentRequestDto requestDto){
        try {
            String link = paymemtService.getPaymentLink(requestDto.getAmount(),
                    requestDto.getRef_id(),
                    requestDto.getDescription(),
                    requestDto.getPhoneNo(),
                    requestDto.getUserName(),
                    requestDto.getEmail());
            return new ResponseEntity<>(link, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}

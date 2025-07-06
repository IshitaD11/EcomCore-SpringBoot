package org.example.paymentservice.controllers;

import org.example.paymentservice.dtos.PaymentRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.paymentservice.services.IPaymemtService;

import java.util.Locale;

@RestController
public class PaymentController {

    @Autowired
    private IPaymemtService paymentService;

    @PostMapping("/payment")
    public ResponseEntity<String> initiatePayment(@RequestBody PaymentRequestDto requestDto){
        try {
            String link = paymentService.getPaymentLink(requestDto.getAmount(),
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

    @GetMapping("/payment/callback")
    public ResponseEntity<Long> handlePaymentCallback(@RequestParam("reference_id") String refId) {
        try {
            Long orderId = Long.valueOf(refId);
            Long confirmedOrderId = paymentService.notifyOrderServiceOfPayment(orderId);
            return ResponseEntity.ok(confirmedOrderId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

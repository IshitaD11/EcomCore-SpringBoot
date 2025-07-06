package org.example.paymentservice.services;

import org.example.paymentservice.paymentGateways.IPaymentGateway;
import org.example.paymentservice.paymentGateways.PaymentGatewaySelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService implements IPaymemtService{

    @Autowired
    private PaymentGatewaySelector paymentGatewaySelector;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getPaymentLink(Long amount,String ref_id,String description, String phoneNo, String userName, String email) {
        IPaymentGateway paymentGateway = paymentGatewaySelector.getPaymentGateway();
        return paymentGateway.getPaymentLink(amount,ref_id,description,phoneNo,userName,email);
    }

    public Long notifyOrderServiceOfPayment(Long orderId) {
        try {
            String url = "http://orderservice/orders/mark-paid/" + orderId;
            restTemplate.postForEntity(url, null, Void.class);
            return orderId; // This can be returned to frontend or processed
        } catch (Exception e) {
            throw new RuntimeException("Failed to notify OrderService: " + e.getMessage());
        }
    }
}

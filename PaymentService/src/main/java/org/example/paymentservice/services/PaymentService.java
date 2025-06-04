package org.example.paymentservice.services;

import org.example.paymentservice.paymentGateways.IPaymentGateway;
import org.example.paymentservice.paymentGateways.PaymentGatewaySelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements IPaymemtService{

    @Autowired
    private PaymentGatewaySelector paymentGatewaySelector;

    @Override
    public String getPaymentLink(Long amount,String ref_id,String description, String phoneNo, String userName, String email) {
        IPaymentGateway paymentGateway = paymentGatewaySelector.getPaymentGateway();
        return paymentGateway.getPaymentLink(amount,ref_id,description,phoneNo,userName,email);
    }
}

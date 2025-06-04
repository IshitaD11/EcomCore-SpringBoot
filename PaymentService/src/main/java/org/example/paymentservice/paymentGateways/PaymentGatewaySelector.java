package org.example.paymentservice.paymentGateways;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentGatewaySelector {

    @Autowired
    private RazorpayPaymentGateway razorpayPaymentGateway;

    public IPaymentGateway getPaymentGateway() {
        return razorpayPaymentGateway;
    }
}

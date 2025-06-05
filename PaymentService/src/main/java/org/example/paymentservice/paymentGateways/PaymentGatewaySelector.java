package org.example.paymentservice.paymentGateways;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PaymentGatewaySelector {

    @Autowired
    private RazorpayPaymentGateway razorpayPaymentGateway;

    @Autowired
    private StripePaymentGateway stripePaymentGateway;

    public IPaymentGateway getPaymentGateway() {
        Random random = new Random();
        if(random.nextInt()%2==0)
            return razorpayPaymentGateway;
        else
            return stripePaymentGateway;
    }
}

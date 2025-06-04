package org.example.paymentservice.paymentGateways;

public interface IPaymentGateway {

    public String getPaymentLink(Long amount,String ref_id,String description, String phoneNo, String name, String email);
}

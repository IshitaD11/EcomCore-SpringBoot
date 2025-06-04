package org.example.paymentservice.services;

public interface IPaymemtService {

    public String getPaymentLink(Long amount,String ref_id,String description, String phoneNo, String userName, String email);
}

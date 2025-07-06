package org.example.paymentservice.paymentGateways;

import com.razorpay.PaymentLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.json.JSONObject;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Component
public class RazorpayPaymentGateway implements IPaymentGateway{

    @Autowired
    private RazorpayClient razorpayClient;

    @Override
    public String getPaymentLink(Long amount,String ref_id,String description, String phoneNo, String name, String email) {
        try {
            System.out.println("Razorpay");
            Long epochtime = System.currentTimeMillis();
            Long expiry = epochtime + 24*60*60*1000;
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount*100 ) ; // converting to paisa
            paymentLinkRequest.put("currency", "INR");
            paymentLinkRequest.put("accept_partial", true);
            paymentLinkRequest.put("first_min_partial_amount", 100);
            paymentLinkRequest.put("expire_by", expiry);
            paymentLinkRequest.put("reference_id", ref_id);
            paymentLinkRequest.put("description", description);
            JSONObject customer = new JSONObject();
            customer.put("name", phoneNo);
            customer.put("contact", name);
            customer.put("email", email);
            paymentLinkRequest.put("customer", customer);
            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);
            paymentLinkRequest.put("reminder_enable", true);
            JSONObject notes = new JSONObject();
//            notes.put("policy_name", "Jeevan Bima");
//            paymentLinkRequest.put("notes", notes);
            paymentLinkRequest.put("callback_url", "https://example-callback-url.com/");
            paymentLinkRequest.put("callback_method", "get");

            PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
            return payment.get("short_url").toString(); // from the response
        }catch (RazorpayException ex){
            throw new RuntimeException(ex.getMessage());
        }

    }
}

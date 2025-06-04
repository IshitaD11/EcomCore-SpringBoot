package org.example.paymentservice.configs;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorpayConfig {

    @Value("${razorpay.key.id}")
    private String razorpayKey;

    @Value("${razorpay.key.secret}")
    private String razorpaySecret;

    @Bean
    public RazorpayClient getRazorpayClient() throws RazorpayException {
        System.out.println("razorpay api key: " + razorpayKey);
        System.out.println("razorpay api secret: " + razorpaySecret);
        return new RazorpayClient(razorpayKey,razorpaySecret);
    }


}

package org.example.orderservice.clients;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaClient {

    private KafkaTemplate<String,String> kafkaTemplate;

    public KafkaTemplate<String, String> getKafkaTemplate() {
        return kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}

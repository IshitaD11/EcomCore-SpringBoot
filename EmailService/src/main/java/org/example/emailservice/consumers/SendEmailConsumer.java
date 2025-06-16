package org.example.emailservice.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.emailservice.dtos.EmailDto;
import org.example.emailservice.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

@Component
public class SendEmailConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${EMAIL_PASSWORD}")
    private String emailPassword;

    @KafkaListener(topics = "user_signup", groupId = "EmailService")
    public void sendEmail(String message){
        try{
            System.out.println("Received message from Kafka: " + message);
            EmailDto emailDto = objectMapper.readValue(message, EmailDto.class);

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            //create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailDto.getFrom(), emailPassword);
                }
            };
            Session session = Session.getInstance(props, auth);

            EmailUtil.sendEmail(session, emailDto.getTo(),emailDto.getSubject(), emailDto.getBody());

            System.out.println("Received message: " + message);
            System.out.println("Using email password: " + (emailPassword != null ? "SET" : "NOT SET"));

        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}

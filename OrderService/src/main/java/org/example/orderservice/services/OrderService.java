package org.example.orderservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.orderservice.clients.KafkaClient;
import org.example.orderservice.models.Order;
import org.example.orderservice.models.OrderItem;
import org.example.orderservice.models.OrderStatus;
import org.example.orderservice.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.example.orderservice.dtos.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaClient kafkaClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Order placeOrder(Long userId, List<OrderRequestItem> items) {

        UserResponseDto user = getUserDetails(userId);

        Order order = new Order();
        order.setUserId(userId);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);

        List<OrderItem> orderItems = new ArrayList<>();
        Double totalAmount = Double.valueOf(0.0);

        for (OrderRequestItem item : items) {
            ResponseEntity<ProductDto> productResp = restTemplate.getForEntity(
                    "http://productcatalogservice/products/{productId}", ProductDto.class, item.getProductId());

            if (productResp.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new RuntimeException("Product not found");
            }

            ProductDto product = productResp.getBody();
            if (product == null) {
                throw new RuntimeException("Product response was empty");
            }

            Double itemTotal = product.getPrice() * item.getQuantity();

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);

            totalAmount = totalAmount + itemTotal.doubleValue();
            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        order = orderRepository.save(order);


        updatePaymentLink(order.getId());
        return order;
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public void markOrderAsPaid(Long orderId) {
        Order order = getOrderById(orderId);
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
    }

    @Override
    public String updatePaymentLink(Long OrderId) {
        Order order = getOrderById(OrderId);
        UserResponseDto user = getUserDetails(order.getUserId());
        try {
            PaymentRequestDto paymentRequest = new PaymentRequestDto();
            paymentRequest.setAmount(order.getTotalAmount().longValue());
            paymentRequest.setRef_id(order.getId().toString());
            paymentRequest.setDescription("Payment for Order ID: " + order.getId());
            paymentRequest.setPhoneNo(user.getPhoneNo()); // Placeholder
            paymentRequest.setUserName(user.getName());
            paymentRequest.setEmail(user.getEmail()); // Placeholder

            String paymentLink = restTemplate.postForObject(
                    "http://paymentservice/payment",
                    paymentRequest,
                    String.class
            );

            order.setPaymentLink(paymentLink);
            return paymentLink;

        } catch (Exception e) {
            System.out.println("Payment service unavailable. Payment link will be generated later.");
            order.setPaymentLink(null);
        }
        return null;
    }


    public UserResponseDto getUserDetails(Long userId) {
        ResponseEntity<UserResponseDto> userResp =
                restTemplate.getForEntity("http://userauthenticationservice/users/{userId}", UserResponseDto.class, userId);
        if (userResp.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new RuntimeException("User not found");
        }

        UserResponseDto user = userResp.getBody();
        return user;
    }

}

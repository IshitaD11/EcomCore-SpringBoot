package org.example.orderservice.controllers;

import org.example.orderservice.dtos.OrderRequestItem;
import org.example.orderservice.dtos.PaymentLinkUpdateRequest;
import org.example.orderservice.models.Order;
import org.example.orderservice.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("/place")
    public Order placeOrder(@RequestParam Long userId, @RequestBody List<OrderRequestItem> items) {
        return orderService.placeOrder(userId, items);
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @PostMapping("/cancel/{orderId}")
    public void cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
    }

    @PostMapping("/mark-paid/{orderId}")
    public void markOrderAsPaid(@PathVariable Long orderId) {
        orderService.markOrderAsPaid(orderId);
    }


    @PostMapping("/{orderId}/update-payment-link")
    public ResponseEntity<String> updatePaymentLink(@PathVariable Long orderId) {
        try {
            String paymentLink = orderService.updatePaymentLink(orderId);
            return ResponseEntity.ok(paymentLink);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update payment link");
        }
    }
}
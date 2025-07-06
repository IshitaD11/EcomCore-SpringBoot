package org.example.orderservice.controllers;

import org.example.orderservice.dtos.OrderItemResponseDto;
import org.example.orderservice.dtos.OrderRequestItem;
import org.example.orderservice.dtos.OrderResponseDto;
import org.example.orderservice.dtos.PaymentLinkUpdateRequest;
import org.example.orderservice.models.Order;
import org.example.orderservice.models.OrderItem;
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
    public ResponseEntity<OrderResponseDto> placeOrder(@RequestParam Long userId, @RequestBody List<OrderRequestItem> items) {
        Order order = orderService.placeOrder(userId, items);
        return ResponseEntity.ok(orderToOrderDto(order));
    }

    private OrderResponseDto orderToOrderDto(Order order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(order.getId());
        orderResponseDto.setUserId(order.getUserId());
        orderResponseDto.setTotalAmount(order.getTotalAmount());
        orderResponseDto.setStatus(order.getStatus().toString());
        orderResponseDto.setItems(orderItemToOrderItemDto(order.getItems()));
        orderResponseDto.setPaymentLink(order.getPaymentLink());
        return orderResponseDto;
    }

    private List<OrderItemResponseDto> orderItemToOrderItemDto(List<OrderItem> items) {
        return items.stream().map(item -> {
            OrderItemResponseDto dto = new OrderItemResponseDto();
            dto.setId(item.getId());
            dto.setProductId(item.getProductId());
            dto.setQuantity(item.getQuantity());
            dto.setPrice(item.getPrice());
            return dto;
        }).toList();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderToOrderDto(order));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> order = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(order.stream()
                .map(this::orderToOrderDto)
                .toList());
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
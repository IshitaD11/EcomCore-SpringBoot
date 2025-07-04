package org.example.orderservice.services;

import org.example.orderservice.dtos.OrderRequestItem;
import org.example.orderservice.models.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId, List<OrderRequestItem> items);
    Order getOrderById(Long orderId);
    List<Order> getOrdersByUserId(Long userId);
    void cancelOrder(Long orderId);
    void markOrderAsPaid(Long orderId);
    String updatePaymentLink(Long orderId);
}

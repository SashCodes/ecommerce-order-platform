package com.sash.ecommerce.orders.controller;

import com.sash.ecommerce.orders.dto.CreateOrderRequest;
import com.sash.ecommerce.orders.dto.OrderResponse;
import com.sash.ecommerce.orders.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse placeOrder(@Valid @RequestBody CreateOrderRequest request) {
        return orderService.placeOrder(request);
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrderById(@PathVariable Long orderId) {
        return orderService.fetchOrderById(orderId);
    }

    @GetMapping("/health")
    public String health() {
        return "Order Service is UP";
    }
}

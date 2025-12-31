package com.sash.ecommerce.orders.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long orderId) {
        super("Order not found for id: " + orderId);
    }
}

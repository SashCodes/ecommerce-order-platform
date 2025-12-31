package com.sash.ecommerce.orders.client.dto;

public class PaymentRequest {

    private Long orderId;
    private double amount;

    public PaymentRequest(Long orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public double getAmount() {
        return amount;
    }
}

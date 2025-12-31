package com.sash.ecommerce.orders.dto;

import java.util.List;

public class OrderResponse {

    private Long orderId;
    private String status;
    private List<OrderItemResponse> items;

    public OrderResponse(Long orderId, String status, List<OrderItemResponse> items) {
        this.orderId = orderId;
        this.status = status;
        this.items = items;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }
}

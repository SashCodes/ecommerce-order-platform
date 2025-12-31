package com.sash.ecommerce.orders.client.dto;

public class ProductResponse {

    private Long productId;
    private String productName;
    private boolean available;

    public ProductResponse() {
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public boolean isAvailable() {
        return available;
    }
}

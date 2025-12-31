package com.sash.ecommerce.product.dto;

public class ProductResponse {

    private Long productId;
    private String productName;
    private boolean available;

    public ProductResponse(Long productId, String productName, boolean available) {
        this.productId = productId;
        this.productName = productName;
        this.available = available;
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

package com.sash.ecommerce.orders.exception;

public class ProductUnavailableException extends RuntimeException {

    public ProductUnavailableException(Long productId) {
        super("Product is not available: " + productId);
    }
}

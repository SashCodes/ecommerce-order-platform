package com.sash.ecommerce.orders.exception;

public class ProductServiceUnavailableException extends RuntimeException {

    public ProductServiceUnavailableException(Long productId) {
        super("Product service unavailable while validating product: " + productId);
    }
}

package com.sash.ecommerce.payment.dto;

public class PaymentResponse {

    private String status;

    public PaymentResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

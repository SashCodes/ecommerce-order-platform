package com.sash.ecommerce.payment.controller;

import com.sash.ecommerce.payment.dto.PaymentRequest;
import com.sash.ecommerce.payment.dto.PaymentResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class PaymentController {

    private final Random random = new Random();

    @PostMapping("/payments")
    public PaymentResponse processPayment(@RequestBody PaymentRequest request) {
        boolean success = random.nextBoolean();
        return new PaymentResponse(success ? "SUCCESS" : "FAILURE");
    }
}

package com.sash.ecommerce.orders.client;

import com.sash.ecommerce.orders.client.dto.PaymentRequest;
import com.sash.ecommerce.orders.client.dto.PaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String paymentServiceBaseUrl;

    public PaymentClient(
            @Value("${payment.service.base-url}") String paymentServiceBaseUrl) {
        this.paymentServiceBaseUrl = paymentServiceBaseUrl;
    }

    public PaymentResponse processPayment(PaymentRequest request) {
        return restTemplate.postForObject(
                paymentServiceBaseUrl + "/payments",
                request,
                PaymentResponse.class
        );
    }
}

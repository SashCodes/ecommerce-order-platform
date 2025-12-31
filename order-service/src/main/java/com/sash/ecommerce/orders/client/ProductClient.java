package com.sash.ecommerce.orders.client;

import com.sash.ecommerce.orders.client.dto.ProductResponse;
import com.sash.ecommerce.orders.exception.ProductServiceUnavailableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String productServiceBaseUrl;

    public ProductClient(
            @Value("${product.service.base-url}") String productServiceBaseUrl) {
        this.productServiceBaseUrl = productServiceBaseUrl;
    }

    public ProductResponse fetchProduct(Long productId) {
        try {
            return restTemplate.getForObject(
                    productServiceBaseUrl + "/products/" + productId,
                    ProductResponse.class
            );
        } catch (RestClientException ex) {
            throw new ProductServiceUnavailableException(productId);
        }
    }
}

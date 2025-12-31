package com.sash.ecommerce.product.controller;

import com.sash.ecommerce.product.dto.ProductResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/products/{productId}")
    public ProductResponse getProduct(@PathVariable Long productId) {

        boolean available = productId % 2 == 0;

        return new ProductResponse(
                productId,
                "Product-" + productId,
                available
        );
    }
}

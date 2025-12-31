package com.sash.ecommerce.orders.service;

import com.sash.ecommerce.orders.client.PaymentClient;
import com.sash.ecommerce.orders.client.ProductClient;
import com.sash.ecommerce.orders.client.dto.PaymentRequest;
import com.sash.ecommerce.orders.client.dto.PaymentResponse;
import com.sash.ecommerce.orders.client.dto.ProductResponse;
import com.sash.ecommerce.orders.dto.CreateOrderRequest;
import com.sash.ecommerce.orders.dto.OrderItemRequest;
import com.sash.ecommerce.orders.exception.ProductUnavailableException;
import com.sash.ecommerce.orders.model.Order;
import com.sash.ecommerce.orders.model.OrderStatus;
import com.sash.ecommerce.orders.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderRepository orderRepository;
    private ProductClient productClient;
    private PaymentClient paymentClient;
    private OrderService orderService;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        productClient = mock(ProductClient.class);
        paymentClient = mock(PaymentClient.class);
        orderService = new OrderService(orderRepository, productClient, paymentClient);
    }

    @Test
    void shouldPlaceOrderSuccessfullyWhenProductAvailableAndPaymentSucceeds() {

        ProductResponse productResponse = new ProductResponse();
        setProductAvailable(productResponse, true);

        PaymentResponse paymentResponse = new PaymentResponse();
        setPaymentStatus(paymentResponse, "SUCCESS");

        when(productClient.fetchProduct(100L)).thenReturn(productResponse);
        when(paymentClient.processPayment(any(PaymentRequest.class)))
                .thenReturn(paymentResponse);

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CreateOrderRequest request = createOrderRequest(100L);

        var response = orderService.placeOrder(request);

        assertEquals(OrderStatus.CONFIRMED.name(), response.getStatus());
        verify(orderRepository, times(2)).save(any(Order.class));
    }

    @Test
    void shouldFailOrderWhenPaymentFails() {

        ProductResponse productResponse = new ProductResponse();
        setProductAvailable(productResponse, true);

        PaymentResponse paymentResponse = new PaymentResponse();
        setPaymentStatus(paymentResponse, "FAILURE");

        when(productClient.fetchProduct(200L)).thenReturn(productResponse);
        when(paymentClient.processPayment(any(PaymentRequest.class)))
                .thenReturn(paymentResponse);

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CreateOrderRequest request = createOrderRequest(200L);

        var response = orderService.placeOrder(request);

        assertEquals(OrderStatus.FAILED.name(), response.getStatus());
        verify(orderRepository, times(2)).save(any(Order.class));
    }

    @Test
    void shouldThrowExceptionWhenProductIsUnavailable() {

        ProductResponse productResponse = new ProductResponse();
        setProductAvailable(productResponse, false);

        when(productClient.fetchProduct(101L)).thenReturn(productResponse);

        CreateOrderRequest request = createOrderRequest(101L);

        assertThrows(ProductUnavailableException.class,
                () -> orderService.placeOrder(request));

        verify(orderRepository, never()).save(any());
        verify(paymentClient, never()).processPayment(any());
    }

    private CreateOrderRequest createOrderRequest(Long productId) {
        OrderItemRequest item = new OrderItemRequest();
        item.setProductId(productId);
        item.setQuantity(1);

        CreateOrderRequest request = new CreateOrderRequest();
        request.setItems(List.of(item));
        return request;
    }

    private void setProductAvailable(ProductResponse response, boolean available) {
        try {
            var field = ProductResponse.class.getDeclaredField("available");
            field.setAccessible(true);
            field.set(response, available);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setPaymentStatus(PaymentResponse response, String status) {
        try {
            var field = PaymentResponse.class.getDeclaredField("status");
            field.setAccessible(true);
            field.set(response, status);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

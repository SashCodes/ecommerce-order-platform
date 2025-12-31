package com.sash.ecommerce.orders.service;

import com.sash.ecommerce.orders.client.PaymentClient;
import com.sash.ecommerce.orders.client.ProductClient;
import com.sash.ecommerce.orders.client.dto.PaymentRequest;
import com.sash.ecommerce.orders.client.dto.PaymentResponse;
import com.sash.ecommerce.orders.client.dto.ProductResponse;
import com.sash.ecommerce.orders.dto.CreateOrderRequest;
import com.sash.ecommerce.orders.dto.OrderItemResponse;
import com.sash.ecommerce.orders.dto.OrderResponse;
import com.sash.ecommerce.orders.exception.OrderNotFoundException;
import com.sash.ecommerce.orders.exception.ProductUnavailableException;
import com.sash.ecommerce.orders.model.Order;
import com.sash.ecommerce.orders.model.OrderItem;
import com.sash.ecommerce.orders.model.OrderStatus;
import com.sash.ecommerce.orders.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;

    public OrderService(OrderRepository orderRepository,
                        ProductClient productClient,
                        PaymentClient paymentClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
        this.paymentClient = paymentClient;
    }

    public OrderResponse placeOrder(CreateOrderRequest request) {

        request.getItems().forEach(item -> {
            ProductResponse product = productClient.fetchProduct(item.getProductId());
            if (!product.isAvailable()) {
                throw new ProductUnavailableException(item.getProductId());
            }
        });

        List<OrderItem> orderedItems = request.getItems()
                .stream()
                .map(item -> new OrderItem(
                        item.getProductId(),
                        item.getQuantity()))
                .collect(Collectors.toList());

        Order order = new Order(OrderStatus.CREATED, orderedItems);
        orderRepository.save(order);

        PaymentRequest paymentRequest =
                new PaymentRequest(order.getId(), orderedItems.size() * 100.0);

        PaymentResponse paymentResponse =
                paymentClient.processPayment(paymentRequest);

        OrderStatus finalStatus =
                "SUCCESS".equals(paymentResponse.getStatus())
                        ? OrderStatus.CONFIRMED
                        : OrderStatus.FAILED;

        order.updateStatus(finalStatus);
        orderRepository.save(order);

        List<OrderItemResponse> responseItems = order.getItems()
                .stream()
                .map(item -> new OrderItemResponse(
                        item.getProductId(),
                        item.getQuantity()))
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                order.getStatus().name(),
                responseItems
        );
    }

    public OrderResponse fetchOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        List<OrderItemResponse> responseItems = order.getItems()
                .stream()
                .map(item -> new OrderItemResponse(
                        item.getProductId(),
                        item.getQuantity()))
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                order.getStatus().name(),
                responseItems
        );
    }
}

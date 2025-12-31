package com.sash.ecommerce.orders.repository;

import com.sash.ecommerce.orders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

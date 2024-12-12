package com.ecommerce.order.orderLine;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
    List<OrderLine> findByOrderId(Integer orderId);

    List<OrderLine> findAllByOrderId(Integer orderId);
}

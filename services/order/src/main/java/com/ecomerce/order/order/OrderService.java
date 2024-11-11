package com.ecomerce.order.order;

import com.ecomerce.order.customer.CustomerClient.CustomerClient;
import com.ecomerce.order.exception.BusinessException;
import com.ecomerce.order.pr.ProductClient;
import com.ecomerce.order.product.ProductClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final com.ecomerce.order.product.ProductClient productClient;
    public Integer createOrder(@Valid OrderRequest request) {
        // check the customer --> openFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: Customer not found"));
        // purchase the products --> product service

        // persist the order

        //  start payment process --> payment service

        // send the order confirmation email --> notification service {kafka}
        return null
    }
}

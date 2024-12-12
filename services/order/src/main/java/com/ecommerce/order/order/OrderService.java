package com.ecommerce.order.order;

import com.ecommerce.order.customer.CustomerClient;
import com.ecommerce.order.exception.BusinessException;
import com.ecommerce.order.kafka.OrderConfirmation;
import com.ecommerce.order.kafka.OrderProducer;
import com.ecommerce.order.orderLine.OrderLineRequest;
import com.ecommerce.order.orderLine.OrderLineService;
import com.ecommerce.order.product.PurchaseRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final com.ecommerce.order.product.ProductClient productClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    @Transactional
    public Integer createOrder(@Valid OrderRequest request) {
        // check the customer --> openFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: Customer not found"));
        // purchase the products --> product service
        var purchasedProducts = this.productClient.purchaseProducts((request.products()));

        var order = this.repository.save(mapper.toOrder(request));
        // persist the order
        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // todo  start payment process --> payment service

        // send the order confirmation email --> notification service {kafka}
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return this.repository.findAll()
                .stream()
                .map(order -> (OrderResponse) this.mapper.fromOrder(order))
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {

    }

//    public List<OrderResponse> findAllOrders() {
//        return this.repository.findAll()
//                .stream()
//                .map(this.mapper::fromOrder)
//                .collect(Collectors.toList());
//    }
}

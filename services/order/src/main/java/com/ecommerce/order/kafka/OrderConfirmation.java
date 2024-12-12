package com.ecommerce.order.kafka;

import com.ecommerce.order.customer.CustomerResponse;
import com.ecommerce.order.order.PaymentMethod;
import com.ecommerce.order.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}

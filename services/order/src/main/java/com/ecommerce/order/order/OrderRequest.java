package com.ecommerce.order.order;

import com.ecommerce.order.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message = "Amount must be greater than zero")
        BigDecimal amount,
        @NotNull(message = "Payment method must be mentioned")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer ID must be mentioned")
        @NotEmpty(message = "Customer ID must be mentioned")
        @NotBlank(message = "Customer ID must be mentioned")
        String customerId,
        @NotEmpty(message = "At least one product must be mentioned")
        List<PurchaseRequest> products
) {
}

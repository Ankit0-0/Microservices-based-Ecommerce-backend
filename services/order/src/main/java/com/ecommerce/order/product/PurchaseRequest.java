package com.ecommerce.order.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotBlank(message = "Product ID must be mentioned")
        Integer productId,
        @Positive(message = "Quantity must be greater than zero")
        Double quantity
) {
}

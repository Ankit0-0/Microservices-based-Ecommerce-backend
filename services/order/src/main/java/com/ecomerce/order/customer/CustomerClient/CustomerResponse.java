package com.ecomerce.order.customer.CustomerClient;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email
) {
}

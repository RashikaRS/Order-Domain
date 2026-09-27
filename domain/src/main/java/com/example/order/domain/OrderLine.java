package com.example.order.domain;

import java.math.BigDecimal;
import java.util.Objects;

public record OrderLine(String productId, BigDecimal unitPrice, int quantity) {
    public OrderLine {
        Objects.requireNonNull(productId, "productId");
        Objects.requireNonNull(unitPrice, "unitPrice");

        if (productId.isBlank()) {
            throw new IllegalArgumentException("Product id cannot be blank");
        }
        if (unitPrice.signum() < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
    }

    public BigDecimal subtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}

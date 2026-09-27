package com.example.order.domain;

import java.util.Objects;

public record OrderId(String value) {
    public OrderId {
        Objects.requireNonNull(value, "value");
        if (value.isBlank()) {
            throw new IllegalArgumentException("Order id cannot be blank");
        }
    }
}

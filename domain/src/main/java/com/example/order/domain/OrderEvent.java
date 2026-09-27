package com.example.order.domain;

import java.time.Instant;

public sealed interface OrderEvent permits OrderConfirmed, PaymentRecorded, OrderCancelled {
    OrderId orderId();
    Instant occurredAt();
}

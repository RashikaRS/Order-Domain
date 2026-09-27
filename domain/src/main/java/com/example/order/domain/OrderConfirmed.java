package com.example.order.domain;

import java.time.Instant;

public record OrderConfirmed(OrderId orderId, Instant occurredAt) implements OrderEvent {}

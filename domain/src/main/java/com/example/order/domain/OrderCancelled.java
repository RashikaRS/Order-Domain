package com.example.order.domain;

import java.time.Instant;

public record OrderCancelled(OrderId orderId, Instant occurredAt) implements OrderEvent {}

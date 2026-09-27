package com.example.order.domain;

import java.time.Instant;

public record PaymentRecorded(OrderId orderId, Instant occurredAt) implements OrderEvent {}

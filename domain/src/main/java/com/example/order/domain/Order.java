package com.example.order.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Order {
    private final OrderId id;
    private final List<OrderLine> lines = new ArrayList<>();
    private OrderStatus status = OrderStatus.DRAFT;

    public Order(OrderId id) {
        this.id = Objects.requireNonNull(id, "id");
    }

    public OrderId id() {
        return id;
    }

    public OrderStatus status() {
        return status;
    }

    public List<OrderLine> lines() {
        return List.copyOf(lines);
    }

    public void addLine(OrderLine line) {
        requireStatus(OrderStatus.DRAFT);
        lines.add(Objects.requireNonNull(line, "line"));
    }

    public BigDecimal total() {
        return lines.stream()
                .map(OrderLine::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public OrderConfirmed confirm(Instant now) {
        Objects.requireNonNull(now, "now");
        requireStatus(OrderStatus.DRAFT);

        if (lines.isEmpty()) {
            throw new IllegalStateException("An order must contain at least one line before confirmation");
        }

        status = OrderStatus.CONFIRMED;
        return new OrderConfirmed(id, now);
    }

    public PaymentRecorded recordPayment(Instant now) {
        Objects.requireNonNull(now, "now");
        requireStatus(OrderStatus.CONFIRMED);

        status = OrderStatus.PAID;
        return new PaymentRecorded(id, now);
    }

    public OrderCancelled cancel(Instant now) {
        Objects.requireNonNull(now, "now");

        if (status == OrderStatus.PAID) {
            throw new IllegalStateException("A paid order cannot be cancelled");
        }
        if (status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("A cancelled order cannot be cancelled again");
        }

        status = OrderStatus.CANCELLED;
        return new OrderCancelled(id, now);
    }

    private void requireStatus(OrderStatus expected) {
        if (status != expected) {
            throw new IllegalStateException(
                    "Invalid transition: " + status + " -> required " + expected);
        }
    }
}

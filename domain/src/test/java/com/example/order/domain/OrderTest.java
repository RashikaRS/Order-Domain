package com.example.order.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private static final Instant NOW = Instant.parse("2026-09-27T10:00:00Z");

    @Test
    void newOrderStartsAsDraft() {
        Order order = new Order(new OrderId("O-1"));
        assertEquals(OrderStatus.DRAFT, order.status());
        assertEquals(BigDecimal.ZERO, order.total());
    }

    @Test
    void confirmationRequiresAtLeastOneLine() {
        Order order = new Order(new OrderId("O-1"));

        assertThrows(IllegalStateException.class, () -> order.confirm(NOW));
        assertEquals(OrderStatus.DRAFT, order.status());
    }

    @Test
    void quantityMustBePositive() {
        assertThrows(IllegalArgumentException.class,
                () -> new OrderLine("P-1", new BigDecimal("10.00"), 0));
        assertThrows(IllegalArgumentException.class,
                () -> new OrderLine("P-1", new BigDecimal("10.00"), -1));
    }

    @Test
    void orderTotalUsesLinePricesAndQuantities() {
        Order order = new Order(new OrderId("O-1"));
        order.addLine(new OrderLine("P-1", new BigDecimal("10.50"), 2));
        order.addLine(new OrderLine("P-2", new BigDecimal("5.00"), 3));

        assertEquals(new BigDecimal("36.00"), order.total());
    }

    @Test
    void confirmedOrderCanBePaid() {
        Order order = orderWithOneLine();
        OrderConfirmed event = order.confirm(NOW);

        assertEquals(OrderStatus.CONFIRMED, order.status());
        assertEquals(new OrderId("O-1"), event.orderId());

        PaymentRecorded payment = order.recordPayment(NOW.plusSeconds(10));
        assertEquals(OrderStatus.PAID, order.status());
        assertEquals(new OrderId("O-1"), payment.orderId());
    }

    @Test
    void paidOrderCannotReturnToDraft() {
        Order order = orderWithOneLine();
        order.confirm(NOW);
        order.recordPayment(NOW.plusSeconds(1));

        assertThrows(IllegalStateException.class,
                () -> order.addLine(new OrderLine("P-2", new BigDecimal("3"), 1)));
        assertThrows(IllegalStateException.class,
                () -> order.confirm(NOW.plusSeconds(2)));
    }

    @Test
    void paidOrderCannotBeCancelled() {
        Order order = orderWithOneLine();
        order.confirm(NOW);
        order.recordPayment(NOW.plusSeconds(1));

        assertThrows(IllegalStateException.class,
                () -> order.cancel(NOW.plusSeconds(2)));
        assertEquals(OrderStatus.PAID, order.status());
    }

    @Test
    void draftOrderCanBeCancelled() {
        Order order = orderWithOneLine();

        OrderCancelled event = order.cancel(NOW);

        assertEquals(OrderStatus.CANCELLED, order.status());
        assertEquals(new OrderId("O-1"), event.orderId());
    }

    private Order orderWithOneLine() {
        Order order = new Order(new OrderId("O-1"));
        order.addLine(new OrderLine("P-1", new BigDecimal("25.00"), 2));
        return order;
    }
}

package com.example.order.adapters.memory;

import com.example.order.domain.Order;
import com.example.order.domain.OrderId;
import com.example.order.domain.OrderLine;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class MemoryAdaptersTest {
    @Test
    void repositoryStoresAndFindsOrder() {
        InMemoryOrderRepository repository = new InMemoryOrderRepository();
        Order order = new Order(new OrderId("O-100"));

        repository.save(order);

        assertTrue(repository.findById(new OrderId("O-100")).isPresent());
        assertEquals(order, repository.findById(new OrderId("O-100")).orElseThrow());
    }

    @Test
    void fixedTimeReturnsConfiguredInstant() {
        Instant instant = Instant.parse("2026-09-27T10:00:00Z");
        FixedTimeAdapter clock = new FixedTimeAdapter(instant);

        assertEquals(instant, clock.now());
    }

    @Test
    void notificationAdapterKeepsPublishedEvents() {
        InMemoryNotificationAdapter adapter = new InMemoryNotificationAdapter();
        Order order = new Order(new OrderId("O-101"));
        order.addLine(new OrderLine("P-1", new BigDecimal("10"), 1));

        var event = order.confirm(Instant.parse("2026-09-27T10:00:00Z"));
        adapter.publish(event);

        assertEquals(1, adapter.events().size());
        assertEquals(event, adapter.events().get(0));
    }
}

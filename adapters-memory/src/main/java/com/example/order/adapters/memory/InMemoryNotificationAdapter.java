package com.example.order.adapters.memory;

import com.example.order.domain.OrderEvent;
import com.example.order.ports.output.NotificationPort;

import java.util.ArrayList;
import java.util.List;

public class InMemoryNotificationAdapter implements NotificationPort {
    private final List<OrderEvent> events = new ArrayList<>();

    @Override
    public void publish(OrderEvent event) {
        events.add(event);
    }

    public List<OrderEvent> events() {
        return List.copyOf(events);
    }
}

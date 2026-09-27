package com.example.order.ports.output;

import com.example.order.domain.OrderEvent;

public interface EventPublisher {
    void publish(OrderEvent event);
}

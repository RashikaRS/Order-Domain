package com.example.order.ports.output;

import com.example.order.domain.OrderEvent;

public interface NotificationPort {
    void publish(OrderEvent event);
}

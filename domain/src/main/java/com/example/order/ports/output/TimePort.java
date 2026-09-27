package com.example.order.ports.output;

import java.time.Instant;

public interface TimePort {
    Instant now();
}

package com.example.order.adapters.memory;

import com.example.order.ports.output.TimePort;

import java.time.Instant;

public class FixedTimeAdapter implements TimePort {
    private final Instant fixedTime;

    public FixedTimeAdapter(Instant fixedTime) {
        this.fixedTime = fixedTime;
    }

    @Override
    public Instant now() {
        return fixedTime;
    }
}

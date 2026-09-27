# Order Domain Architecture Task

A Java 17 multi-module implementation of a non-trivial order approval workflow.

## Requirements implemented

- States: `DRAFT`, `CONFIRMED`, `PAID`, `CANCELLED`
- An order must have at least one line before confirmation.
- Quantity must be positive.
- A cancelled order cannot be paid.
- A paid order cannot return to draft.
- Order total is calculated from immutable line prices and quantities.
- Domain events: `OrderConfirmed`, `PaymentRecorded`, `OrderCancelled`
- Output ports for persistence, notifications, events, and time.
- In-memory adapters for repository, notification, and time.
- JUnit 5 tests covering legal and illegal transitions.
- Architecture Decision Records documenting the domain boundary.

## Run tests

Requires JDK 17+ and Maven.

```bash
mvn test
```

## Architecture

```text
                    +----------------------+
                    |      domain          |
                    |----------------------|
                    | Order / rules/events  |
                    | output ports          |
                    +----------+-----------+
                               ^
                               |
                    +----------+-----------+
                    |   adapters-memory    |
                    |----------------------|
                    | repository           |
                    | notification         |
                    | fixed time           |
                    +----------------------+
```

The domain module has no Spring, JPA, HTTP, or database dependency.

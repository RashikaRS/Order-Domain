# ADR 0001: Keep the Domain Module Independent

## Status
Accepted

## Context
The order workflow contains business rules that should remain independent from Spring,
JPA, HTTP, and database technologies.

## Decision
The domain module uses plain Java 17 classes, records, enums, and interfaces.
External concerns are represented by output ports such as repository, notification,
event publishing, and time ports. Infrastructure implementations live outside the
domain module.

## Consequences
- Domain rules are easy to unit test.
- Infrastructure can be replaced without changing business rules.
- The project can later add Spring, JPA, HTTP, or another database as adapters.

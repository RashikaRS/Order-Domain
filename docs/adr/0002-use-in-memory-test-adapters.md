# ADR 0002: Use In-Memory Adapters for Tests

## Status
Accepted

## Context
The domain task requires persistence, notifications, and time ports plus adapters that
can be used in tests without external services.

## Decision
The adapters-memory module provides an in-memory repository, notification adapter,
and fixed-time adapter.

## Consequences
Tests remain deterministic and do not require a database or network connection.

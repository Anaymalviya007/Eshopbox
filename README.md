# Thread-Safe Rate Limiter

A Java rate limiter that limits the number of requests to a set value per rolling one-second window.

## Thread Safety

- The `allowRequest()` method is `synchronized`.
- This allows only one thread to check or change the shared queue at a time.
- It prevents two threads from accepting requests at the same time and exceeding the limit.
- Queue cleanup, limit checks, and timestamp updates are done as one operation.

## Design Choices

- `ArrayDeque`: Stores request timestamps.
- `System.nanoTime()`: Measures elapsed time without system clock changes.
- `synchronized`: Protects shared data from concurrent access.

## Limitations

- The limit applies to one shared instance, not multiple JVMs.



// # Eshopbox — Backend Engineering Test

// **Time:** 90 minutes · **Submit:** a GitHub repo link

// Use any language you're comfortable in. Java is a plus. You may use documentation.

// ---

// ## The problem — Rate limiter

// We call external sales-channel APIs to sync inventory. Each channel limits how many calls we can make: **no more than N calls per second.** If we exceed the limit, the channel blocks us.

// Inside our system, **many threads try to make these calls at the same time.** We need one shared rate limiter that decides whether each call is allowed.

// Build a rate limiter with this method:

// ```
// boolean allowRequest()
// ```

// It returns `true` if we are still under the limit for the current second, and `false` if allowing the call would exceed the limit. The limit `N` is configurable.

// **The key requirement:** it must be correct when many threads call `allowRequest()` at the same time. Never allow more than `N` calls to succeed in a single second, no matter how many threads are calling at once.

// **What to submit:**
// - Your rate limiter implementation.
// - A short note (in the README) explaining how you made it safe for many threads calling at once, and anything you were unsure about.

// ---

// Submit as a GitHub repo link.
import java.util.ArrayDeque;

public class RateLimiter {

    private final int limit; // N
    private final ArrayDeque<Long> times = new ArrayDeque<>(); // the queue

    public RateLimiter(int limit) {
        this.limit = limit;
    }

    public synchronized boolean allowRequest() {
        long now = System.nanoTime();

        // Step 1: remove times that are 1 second old or older.
        while (!times.isEmpty() && now - times.peekFirst() >= 1_000_000_000L) {
            times.pollFirst();
        }

        // Step 2: if the queue is full, reject.
        if (times.size() >= limit) {
            return false;
        }

        // Step 3: save the time. Allow the call.
        times.addLast(now);
        return true;
    }
}

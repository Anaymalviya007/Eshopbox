public class Main {

    public static void main(String[] args) throws Exception {
        RateLimiter limiter = new RateLimiter(10);   // limit = 10 per second
        long start = System.nanoTime();

        Thread[] threads = new Thread[5];

        for (int t = 0; t < 5; t++) {
            final int threadId = t + 1;
            threads[t] = new Thread(() -> {
                for (int j = 1; j <= 4; j++) {
                    boolean ok = limiter.allowRequest();
                    long ms = (System.nanoTime() - start) / 1_000_000;
                    System.out.println("Thread " + threadId + ", call " + j
                            + " at " + ms + " ms: " + (ok ? "ACCEPTED" : "REJECTED"));
                }
            });
            threads[t].start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }
}
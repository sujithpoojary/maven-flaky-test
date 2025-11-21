package com.example.calculator;

import org.junit.jupiter.api.Test;
import java.util.concurrent.*;
import static org.assertj.core.api.Assertions.*;

class CalculatorConcurrencyTest {
    private final Calculator calculator = new Calculator();

    @Test void concurrentAdditions() throws Exception {
        int tasks = 100;
        ExecutorService exec = Executors.newFixedThreadPool(8);
        CountDownLatch start = new CountDownLatch(1);
        CompletableFuture<Void>[] futures = new CompletableFuture[tasks];
        for (int i=0;i<tasks;i++) {
            final int v = i;
            futures[i] = CompletableFuture.runAsync(() -> {
                try { start.await(); } catch (InterruptedException ignored) {}
                int res = calculator.add(v, v+1);
                assertThat(res).isEqualTo(v + (v+1));
            }, exec);
        }
        start.countDown();
        CompletableFuture.allOf(futures).get(5, TimeUnit.SECONDS);
        exec.shutdownNow();
    }

    @Test void concurrentMixedOperations() throws Exception {
        int tasks = 100;
        ExecutorService exec = Executors.newFixedThreadPool(8);
        CountDownLatch start = new CountDownLatch(1);
        CompletableFuture<Void>[] futures = new CompletableFuture[tasks];
        for (int i=0;i<tasks;i++) {
            final int v = i+1;
            futures[i] = CompletableFuture.runAsync(() -> {
                try { start.await(); } catch (InterruptedException ignored) {}
                int a = calculator.add(v,2);
                int s = calculator.subtract(a,1);
                int m = calculator.multiply(s,3);
                int d = calculator.divide(m,3);
                assertThat(d).isEqualTo(m/3);
            }, exec);
        }
        start.countDown();
        CompletableFuture.allOf(futures).get(5, TimeUnit.SECONDS);
        exec.shutdownNow();
    }
}


package com.example.calculator;

import org.junit.jupiter.api.*;
import java.util.concurrent.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Intentionally flaky UNIT tests to demonstrate Surefire's rerun capability.
 * These tests are disabled by default to prevent breaking builds.
 *
 * To test the flaky behavior:
 * 1. Uncomment @Disabled annotations
 * 2. Run: mvn test -Dsurefire.rerunFailingTestsCount=5
 * 3. Or just: mvn test (uses pom.xml config)
 */
class CalculatorDisabledFlakyTest {
    private final Calculator calculator = new Calculator();

    //@Disabled("Intentionally flaky: relies on timing and random sleep")
    @Test void flakyAdditionTimingTest() throws Exception {
        // Flaky: Uses current timestamp to determine pass/fail - fails roughly 40% of the time
        long nanos = System.nanoTime();
        long sleep = nanos % 50;
        Thread.sleep(sleep);

        int result = calculator.add(2, 3);
        assertThat(result).isEqualTo(5);

        // This assertion will fail if the last digit of nanoTime is >= 6
        assertThat(nanos % 10).as("Flaky assertion based on nanoTime last digit").isLessThan(6);
    }

    //@Disabled("Intentionally flaky: millisecond-based randomness")
    @Test void flakySubtractionMillisTest() {
        // Flaky: Succeeds/fails based on whether current millisecond is even or odd
        long currentMillis = System.currentTimeMillis();

        int result = calculator.subtract(10, 4);
        assertThat(result).isEqualTo(6);

        // This will fail roughly 50% of the time
        assertThat(currentMillis % 2).as("Flaky: fails when millisecond is odd").isEqualTo(0);
    }

    //@Disabled("Intentionally flaky: hashCode-based randomness")
    @Test void flakyMultiplicationHashCodeTest() {
        // Flaky: Fails based on Object hashCode which varies between JVM runs
        Object randomObject = new Object();
        int hash = randomObject.hashCode();

        int result = calculator.multiply(7, 6);
        assertThat(result).isEqualTo(42);

        // Fails if hashCode is divisible by 3 (roughly 33% failure rate)
        assertThat(hash % 3).as("Flaky: fails when hashCode divisible by 3").isNotEqualTo(0);
    }

    //@Disabled("Intentionally flaky: thread scheduling dependent")
    @Test void flakyDivisionThreadRaceTest() throws Exception {
        // Flaky: Race condition between two threads
        final int[] counter = {0};
        Thread t1 = new Thread(() -> counter[0]++);
        Thread t2 = new Thread(() -> counter[0]++);

        t1.start();
        t2.start();

        // Tiny race window - sometimes t1 completes before assertion, sometimes not
        Thread.sleep(0, 100); // 100 nanoseconds - very short

        int result = calculator.divide(8, 2);
        assertThat(result).isEqualTo(4);

        t1.join();
        t2.join();

        // This assertion timing is unpredictable
        assertThat(counter[0]).as("Flaky: race condition on counter").isEqualTo(2);
    }

    //@Disabled("Intentionally flaky: based on nanoTime modulo")
    @Test void flakyCalculationNanoModTest() {
        // Flaky: Fails based on nanoTime % 7
        long nanos = System.nanoTime();

        int sum = calculator.add(10, 20);
        int product = calculator.multiply(3, 4);

        assertThat(sum).isEqualTo(30);
        assertThat(product).isEqualTo(12);

        // Fails roughly 1/7 times (14% failure rate)
        assertThat(nanos % 7).as("Flaky: fails when nanos divisible by 7").isNotEqualTo(0);
    }

    @Disabled("Intentionally flaky: thread interleaving")
    @Test void flakyConcurrentCalculationsTest() throws Exception {
        // Flaky: Multiple threads with timing-dependent assertions
        ExecutorService executor = Executors.newFixedThreadPool(2);
        final boolean[] threadCompleted = {false, false};

        Future<?> future1 = executor.submit(() -> {
            calculator.add(1, 1);
            threadCompleted[0] = true;
        });

        Future<?> future2 = executor.submit(() -> {
            calculator.multiply(2, 2);
            threadCompleted[1] = true;
        });

        // Very short timeout - may not complete on slower systems
        Thread.sleep(0, 500); // 500 nanoseconds

        // Flaky: Depends on thread completion timing
        assertThat(threadCompleted[0] || threadCompleted[1])
            .as("Flaky: at least one thread should complete quickly")
            .isTrue();

        future1.get(100, TimeUnit.MILLISECONDS);
        future2.get(100, TimeUnit.MILLISECONDS);
        executor.shutdownNow();
    }

    @Disabled("Intentionally flaky: system time-based")
    @Test void flakySystemTimeBasedTest() {
        // Flaky: Based on current second value
        long currentSeconds = System.currentTimeMillis() / 1000;

        int result = calculator.subtract(100, 25);
        assertThat(result).isEqualTo(75);

        // Fails if current second is divisible by 5 (20% failure rate)
        assertThat(currentSeconds % 5).as("Flaky: fails when second divisible by 5").isNotEqualTo(0);
    }

    @Disabled("Intentionally flaky: Object identity hashCode")
    @Test void flakyObjectIdentityTest() {
        // Flaky: Based on System.identityHashCode
        Object obj1 = new Object();
        Object obj2 = new Object();
        int identity = System.identityHashCode(obj1) + System.identityHashCode(obj2);

        int sum = calculator.add(5, 5);
        assertThat(sum).isEqualTo(10);

        // Flaky: Fails if combined identity hash is even (50% failure rate)
        assertThat(identity % 2).as("Flaky: fails when combined identity hash is even").isNotEqualTo(0);
    }

    @Disabled("Intentionally flaky: GC-dependent timing")
    @Test void flakyMemoryDependentTest() throws Exception {
        // Flaky: May behave differently based on GC activity
        long startTime = System.nanoTime();

        // Create some objects to potentially trigger GC
        for (int i = 0; i < 1000; i++) {
            Object unused = new Object();
        }

        int result = calculator.multiply(9, 9);
        assertThat(result).isEqualTo(81);

        long elapsed = System.nanoTime() - startTime;

        // Flaky: Timing varies based on GC and system load
        assertThat(elapsed).as("Flaky: operation should complete quickly")
            .isLessThan(1_000_000); // 1ms - may fail on slow/busy systems
    }

    @Disabled("Intentionally flaky: random thread sleep variation")
    @Test void flakyRandomSleepVariationTest() throws Exception {
        // Flaky: Based on nanoTime-derived sleep duration
        long nanos = System.nanoTime();
        long sleepMs = (nanos % 10); // 0-9 milliseconds

        Thread.sleep(sleepMs);

        int result = calculator.divide(100, 10);
        assertThat(result).isEqualTo(10);

        // Flaky: Fails if sleep was >= 5ms (50% failure rate)
        assertThat(sleepMs).as("Flaky: fails when sleep >= 5ms").isLessThan(5);
    }
}


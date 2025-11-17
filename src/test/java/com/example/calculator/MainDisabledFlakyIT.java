package com.example.calculator;

import org.junit.jupiter.api.*;
import java.io.*;
import static org.assertj.core.api.Assertions.*;

class MainDisabledFlakyIT {
    private PrintStream originalOut; private PrintStream originalErr; private ByteArrayOutputStream outContent; private ByteArrayOutputStream errContent;
    @BeforeEach void setUp(){ originalOut=System.out; originalErr=System.err; outContent=new ByteArrayOutputStream(); errContent=new ByteArrayOutputStream(); System.setOut(new PrintStream(outContent)); System.setErr(new PrintStream(errContent)); }
    @AfterEach void tearDown(){ System.setOut(originalOut); System.setErr(originalErr); }
    private String out(){ return outContent.toString(); } private String err(){ return errContent.toString(); }

    @Disabled("Intentionally flaky: relies on timing and random sleep")
    @Test void flakyRandomSleepTest() throws Exception {
        // Flaky: Uses current timestamp to determine pass/fail - fails roughly 40% of the time
        long nanos = System.nanoTime();
        long sleep = nanos % 50;
        Thread.sleep(sleep);
        Main.main(new String[]{"2","+","3"});
        // This assertion will fail if the last digit of nanoTime is >= 6
        assertThat(nanos % 10).as("Flaky assertion based on nanoTime last digit").isLessThan(6);
    }

    @Disabled("Intentionally flaky: arbitrary nanoTime comparison")
    @Test void flakyTimingDependentTest() {
        // Flaky: Succeeds/fails based on whether current millisecond is even or odd
        long currentMillis = System.currentTimeMillis();
        Main.main(new String[]{"5","-","3"});
        assertThat(out()).contains("5 - 3 = 2");
        // This will fail roughly 50% of the time
        assertThat(currentMillis % 2).as("Flaky: fails when millisecond is odd").isEqualTo(0);
    }

    @Disabled("Intentionally flaky: hashCode-based randomness")
    @Test void flakyHashCodeTest() throws Exception {
        // Flaky: Fails based on Object hashCode which varies between JVM runs
        Object randomObject = new Object();
        int hash = randomObject.hashCode();
        Main.main(new String[]{"10","*","5"});
        assertThat(out()).contains("10 * 5 = 50");
        // Fails if hashCode is divisible by 3 (roughly 33% failure rate)
        assertThat(hash % 3).as("Flaky: fails when hashCode divisible by 3").isNotEqualTo(0);
    }

    @Disabled("Intentionally flaky: thread scheduling dependent")
    @Test void flakyThreadRaceTest() throws Exception {
        // Flaky: Race condition between two threads
        final int[] counter = {0};
        Thread t1 = new Thread(() -> { counter[0]++; });
        Thread t2 = new Thread(() -> { counter[0]++; });
        t1.start(); t2.start();
        // Tiny race window - sometimes t1 completes before assertion, sometimes not
        Thread.sleep(0, 100); // 100 nanoseconds - very short
        t1.join(); t2.join();
        Main.main(new String[]{"8","/","4"});
        // This assertion timing is unpredictable
        assertThat(counter[0]).as("Flaky: race condition on counter").isEqualTo(2);
    }
}


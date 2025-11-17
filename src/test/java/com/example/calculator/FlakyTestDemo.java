package com.example.calculator;

public class FlakyTestDemo {
    public static void main(String[] args) {
        System.out.println("=== Demonstrating Flaky Test Behavior ===\n");

        int runs = 20;
        int[] failureTypes = new int[4];

        for (int i = 1; i <= runs; i++) {
            System.out.print("Run " + i + ": ");

            // Test 1: nanoTime % 10 < 6 (fails ~40% of time)
            long nanos = System.nanoTime();
            boolean test1Pass = (nanos % 10) < 6;
            if (!test1Pass) failureTypes[0]++;

            // Test 2: currentTimeMillis % 2 == 0 (fails ~50% of time)
            long millis = System.currentTimeMillis();
            boolean test2Pass = (millis % 2) == 0;
            if (!test2Pass) failureTypes[1]++;

            // Test 3: Object.hashCode() % 3 != 0 (fails ~33% of time)
            Object obj = new Object();
            boolean test3Pass = (obj.hashCode() % 3) != 0;
            if (!test3Pass) failureTypes[2]++;

            // Test 4: Race condition (unpredictable)
            final int[] counter = {0};
            Thread t1 = new Thread(() -> counter[0]++);
            Thread t2 = new Thread(() -> counter[0]++);
            t1.start();
            t2.start();
            try {
                Thread.sleep(0, 100); // 100 nanoseconds
                t1.join();
                t2.join();
            } catch (InterruptedException e) {}
            boolean test4Pass = counter[0] == 2;
            if (!test4Pass) failureTypes[3]++;

            boolean allPass = test1Pass && test2Pass && test3Pass && test4Pass;

            if (allPass) {
                System.out.println("✓ ALL PASS");
            } else {
                System.out.print("✗ FAIL - Failed: ");
                if (!test1Pass) System.out.print("nanoTime ");
                if (!test2Pass) System.out.print("millis ");
                if (!test3Pass) System.out.print("hashCode ");
                if (!test4Pass) System.out.print("race ");
                System.out.println();
            }

            // Small delay to vary timing
            try { Thread.sleep(5); } catch (InterruptedException e) {}
        }

        System.out.println("\n=== Failure Statistics ===");
        System.out.println("Test 1 (nanoTime % 10 < 6):      " + failureTypes[0] + "/" + runs + " runs failed");
        System.out.println("Test 2 (millis % 2 == 0):        " + failureTypes[1] + "/" + runs + " runs failed");
        System.out.println("Test 3 (hashCode % 3 != 0):      " + failureTypes[2] + "/" + runs + " runs failed");
        System.out.println("Test 4 (race condition):         " + failureTypes[3] + "/" + runs + " runs failed");

        int totalFails = failureTypes[0] + failureTypes[1] + failureTypes[2] + failureTypes[3];
        System.out.println("\nAt least one test failed in most runs, demonstrating FLAKY behavior!");
    }
}


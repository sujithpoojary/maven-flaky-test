package com.example.calculator;

import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.assertj.core.api.Assertions.*;

class CalculatorRandomizedTest {
    private final Calculator calculator = new Calculator();
    private final Random rnd = new Random(12345);

    @Test void randomizedAdditionSeeded() {
        for (int i=0;i<100;i++) {
            int a = rnd.nextInt();
            int b = rnd.nextInt();
            assertThat(calculator.add(a,b)).isEqualTo(a + b);
        }
    }

    @Test void randomizedMixedOpsSeeded() {
        for (int i=0;i<200;i++) {
            int a = rnd.nextInt();
            int b = rnd.nextInt();
            int c = rnd.nextInt();
            int r = calculator.add(a,b);
            r = calculator.subtract(r,c);
            r = calculator.multiply(r, (c==0?1:c));
            // Avoid division by zero; use absolute to keep within range
            int d = (b==0?1:Math.abs(b));
            int div = calculator.divide(r,d);
            assertThat(div).isEqualTo(r / d);
        }
    }
}


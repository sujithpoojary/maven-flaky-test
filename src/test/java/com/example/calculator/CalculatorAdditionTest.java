package com.example.calculator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CalculatorAdditionTest {
    private final Calculator calculator = new Calculator();

    @Test
    void addAssociativity() {
        int a = 10, b = 20, c = 30;
        assertThat(calculator.add(calculator.add(a, b), c)).isEqualTo(calculator.add(a, calculator.add(b, c)));
    }

    @Test
    void addCommutativity() {
        assertThat(calculator.add(123, 456)).isEqualTo(calculator.add(456, 123));
    }

    @Test
    void addOverflowWrapAround() {
        assertThat(calculator.add(Integer.MAX_VALUE, 1)).isEqualTo(Integer.MIN_VALUE);
    }

    @Test
    void addMaxValueAndNegative() {
        assertThat(calculator.add(Integer.MAX_VALUE, -10)).isEqualTo(2147483637);
    }

    @Test
    void addMinValuePlusOne() {
        assertThat(calculator.add(Integer.MIN_VALUE, 1)).isEqualTo(-2147483647);
    }

    @Test
    void addLargePositive() {
        assertThat(calculator.add(100_000, 234_567)).isEqualTo(334_567);
    }

    @Test
    void addWithNegative() {
        assertThat(calculator.add(5, -2)).isEqualTo(3);
    }

    @Test
    void addZeroIdentity() {
        assertThat(calculator.add(5, 0)).isEqualTo(5);
    }

    @Test
    void addSmallPositive() {
        assertThat(calculator.add(3, 4)).isEqualTo(7);
    }
}

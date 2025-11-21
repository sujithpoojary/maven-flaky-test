package com.example.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Calculator Boundary Value Tests")
class CalculatorBoundaryTest {
    private final Calculator calculator = new Calculator();

    @Test
    @DisplayName("Add at integer maximum boundary")
    void addAtMaxBoundary() {
        assertThat(calculator.add(Integer.MAX_VALUE, 0)).isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    @DisplayName("Subtract from integer maximum")
    void subtractFromMax() {
        assertThat(calculator.subtract(Integer.MAX_VALUE, 100)).isEqualTo(2147483547);
    }

    @Test
    @DisplayName("Multiply max value by zero")
    void multiplyMaxByZero() {
        assertThat(calculator.multiply(Integer.MAX_VALUE, 0)).isEqualTo(0);
    }

    @Test
    @DisplayName("Multiply max value by one")
    void multiplyMaxByOne() {
        assertThat(calculator.multiply(Integer.MAX_VALUE, 1)).isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    @DisplayName("Divide max value by itself")
    void divideMaxByItself() {
        assertThat(calculator.divide(Integer.MAX_VALUE, Integer.MAX_VALUE)).isEqualTo(1);
    }

    @Test
    @DisplayName("Add at integer minimum boundary")
    void addAtMinBoundary() {
        assertThat(calculator.add(Integer.MIN_VALUE, 0)).isEqualTo(Integer.MIN_VALUE);
    }

    @Test
    @DisplayName("Subtract from integer minimum causes underflow")
    void subtractFromMinUnderflow() {
        assertThat(calculator.subtract(Integer.MIN_VALUE, 1)).isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    @DisplayName("Multiply min value by zero")
    void multiplyMinByZero() {
        assertThat(calculator.multiply(Integer.MIN_VALUE, 0)).isEqualTo(0);
    }

    @Test
    @DisplayName("Multiply min value by one")
    void multiplyMinByOne() {
        assertThat(calculator.multiply(Integer.MIN_VALUE, 1)).isEqualTo(Integer.MIN_VALUE);
    }

    @Test
    @DisplayName("Divide min value by itself")
    void divideMinByItself() {
        assertThat(calculator.divide(Integer.MIN_VALUE, Integer.MIN_VALUE)).isEqualTo(1);
    }

    @Test
    @DisplayName("Add near max boundary positive overflow")
    void addNearMaxOverflow() {
        assertThat(calculator.add(Integer.MAX_VALUE - 5, 10)).isLessThan(0);
    }

    @Test
    @DisplayName("Subtract near min boundary negative underflow")
    void subtractNearMinUnderflow() {
        assertThat(calculator.subtract(Integer.MIN_VALUE + 5, 10)).isGreaterThan(0);
    }

    @Test
    @DisplayName("Multiply large positive numbers overflow")
    void multiplyLargePositiveOverflow() {
        assertThat(calculator.multiply(100000, 100000)).isNotEqualTo(10000000000L);
    }

    @Test
    @DisplayName("Divide by negative one")
    void divideByNegativeOne() {
        assertThat(calculator.divide(100, -1)).isEqualTo(-100);
    }
}


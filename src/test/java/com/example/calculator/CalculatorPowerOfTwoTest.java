package com.example.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Calculator Power of Two Operations Tests")
class CalculatorPowerOfTwoTest {
    private final Calculator calculator = new Calculator();

    @Test
    @DisplayName("Multiply by power of 2: 2^1")
    void multiplyByTwo() {
        assertThat(calculator.multiply(15, 2)).isEqualTo(30);
    }

    @Test
    @DisplayName("Multiply by power of 2: 2^2")
    void multiplyByFour() {
        assertThat(calculator.multiply(15, 4)).isEqualTo(60);
    }

    @Test
    @DisplayName("Multiply by power of 2: 2^3")
    void multiplyByEight() {
        assertThat(calculator.multiply(15, 8)).isEqualTo(120);
    }

    @Test
    @DisplayName("Multiply by power of 2: 2^4")
    void multiplyBySixteen() {
        assertThat(calculator.multiply(15, 16)).isEqualTo(240);
    }

    @Test
    @DisplayName("Divide by power of 2: 2^1")
    void divideByTwo() {
        assertThat(calculator.divide(100, 2)).isEqualTo(50);
    }

    @Test
    @DisplayName("Divide by power of 2: 2^2")
    void divideByFour() {
        assertThat(calculator.divide(100, 4)).isEqualTo(25);
    }

    @Test
    @DisplayName("Divide by power of 2: 2^3")
    void divideByEight() {
        assertThat(calculator.divide(64, 8)).isEqualTo(8);
    }

    @Test
    @DisplayName("Divide by power of 2: 2^4")
    void divideBySixteen() {
        assertThat(calculator.divide(256, 16)).isEqualTo(16);
    }

    @Test
    @DisplayName("Add powers of two: 2 + 4")
    void addPowersOfTwo() {
        assertThat(calculator.add(2, 4)).isEqualTo(6);
    }

    @Test
    @DisplayName("Subtract powers of two: 16 - 8")
    void subtractPowersOfTwo() {
        assertThat(calculator.subtract(16, 8)).isEqualTo(8);
    }

    @Test
    @DisplayName("Multiply two powers of two: 4 * 8")
    void multiplyTwoPowersOfTwo() {
        assertThat(calculator.multiply(4, 8)).isEqualTo(32);
    }

    @Test
    @DisplayName("Divide two powers of two: 64 / 16")
    void divideTwoPowersOfTwo() {
        assertThat(calculator.divide(64, 16)).isEqualTo(4);
    }
}


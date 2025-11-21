package com.example.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Calculator Symmetry and Properties Tests")
class CalculatorSymmetryTest {
    private final Calculator calculator = new Calculator();

    @Test
    @DisplayName("Addition symmetry: a + b = b + a")
    void additionSymmetry() {
        assertThat(calculator.add(17, 25)).isEqualTo(calculator.add(25, 17));
    }

    @Test
    @DisplayName("Multiplication symmetry: a * b = b * a")
    void multiplicationSymmetry() {
        assertThat(calculator.multiply(7, 9)).isEqualTo(calculator.multiply(9, 7));
    }

    @Test
    @DisplayName("Subtraction non-symmetry: a - b != b - a")
    void subtractionNonSymmetry() {
        assertThat(calculator.subtract(10, 3)).isNotEqualTo(calculator.subtract(3, 10));
    }

    @Test
    @DisplayName("Division non-symmetry: a / b != b / a")
    void divisionNonSymmetry() {
        assertThat(calculator.divide(20, 4)).isNotEqualTo(calculator.divide(4, 20));
    }

    @Test
    @DisplayName("Zero addition identity: a + 0 = a")
    void zeroAdditionIdentity() {
        assertThat(calculator.add(42, 0)).isEqualTo(42);
    }

    @Test
    @DisplayName("Zero multiplication property: a * 0 = 0")
    void zeroMultiplicationProperty() {
        assertThat(calculator.multiply(999, 0)).isEqualTo(0);
    }

    @Test
    @DisplayName("One multiplication identity: a * 1 = a")
    void oneMultiplicationIdentity() {
        assertThat(calculator.multiply(123, 1)).isEqualTo(123);
    }

    @Test
    @DisplayName("Inverse addition: a + (-a) = 0")
    void inverseAddition() {
        assertThat(calculator.add(50, -50)).isEqualTo(0);
    }

    @Test
    @DisplayName("Subtraction as inverse addition: a - b = a + (-b)")
    void subtractionAsInverseAddition() {
        int a = 100, b = 30;
        assertThat(calculator.subtract(a, b)).isEqualTo(calculator.add(a, -b));
    }

    @Test
    @DisplayName("Division by one identity: a / 1 = a")
    void divisionByOneIdentity() {
        assertThat(calculator.divide(789, 1)).isEqualTo(789);
    }

    @Test
    @DisplayName("Distributive property: a * (b + c) = a*b + a*c")
    void distributiveProperty() {
        int a = 5, b = 3, c = 7;
        int left = calculator.multiply(a, calculator.add(b, c));
        int right = calculator.add(calculator.multiply(a, b), calculator.multiply(a, c));
        assertThat(left).isEqualTo(right);
    }

    @Test
    @DisplayName("Subtraction inverse: a - a = 0")
    void subtractionInverse() {
        assertThat(calculator.subtract(77, 77)).isEqualTo(0);
    }
}


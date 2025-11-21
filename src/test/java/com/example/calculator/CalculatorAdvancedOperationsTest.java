package com.example.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Calculator Advanced Operations Tests")
class CalculatorAdvancedOperationsTest {
    private final Calculator calculator = new Calculator();

    @Test
    @DisplayName("Multiply then add")
    void multiplyThenAdd() {
        int result = calculator.multiply(5, 3);
        assertThat(calculator.add(result, 10)).isEqualTo(25);
    }

    @Test
    @DisplayName("Add then subtract")
    void addThenSubtract() {
        int result = calculator.add(100, 50);
        assertThat(calculator.subtract(result, 30)).isEqualTo(120);
    }

    @Test
    @DisplayName("Divide then multiply")
    void divideThenMultiply() {
        int result = calculator.divide(100, 5);
        assertThat(calculator.multiply(result, 3)).isEqualTo(60);
    }

    @Test
    @DisplayName("Subtract then divide")
    void subtractThenDivide() {
        int result = calculator.subtract(100, 40);
        assertThat(calculator.divide(result, 6)).isEqualTo(10);
    }

    @Test
    @DisplayName("Complex chain: add, multiply, divide")
    void complexChainAddMultiplyDivide() {
        int step1 = calculator.add(10, 5);
        int step2 = calculator.multiply(step1, 4);
        assertThat(calculator.divide(step2, 12)).isEqualTo(5);
    }

    @Test
    @DisplayName("Complex chain: multiply, subtract, add")
    void complexChainMultiplySubtractAdd() {
        int step1 = calculator.multiply(7, 8);
        int step2 = calculator.subtract(step1, 16);
        assertThat(calculator.add(step2, 4)).isEqualTo(44);
    }

    @Test
    @DisplayName("Chain with negative intermediate result")
    void chainWithNegativeIntermediate() {
        int step1 = calculator.subtract(10, 50);
        assertThat(calculator.multiply(step1, 2)).isEqualTo(-80);
    }

    @Test
    @DisplayName("Multiple additions in sequence")
    void multipleAdditionsSequence() {
        int result = 0;
        for (int i = 1; i <= 5; i++) {
            result = calculator.add(result, i);
        }
        assertThat(result).isEqualTo(15);
    }

    @Test
    @DisplayName("Multiple multiplications in sequence")
    void multipleMultiplicationsSequence() {
        int result = 1;
        for (int i = 1; i <= 5; i++) {
            result = calculator.multiply(result, 2);
        }
        assertThat(result).isEqualTo(32);
    }

    @Test
    @DisplayName("Alternating add and subtract")
    void alternatingAddSubtract() {
        int result = calculator.add(100, 10);
        result = calculator.subtract(result, 5);
        result = calculator.add(result, 20);
        result = calculator.subtract(result, 15);
        assertThat(result).isEqualTo(110);
    }
}


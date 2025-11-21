package com.example.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Calculator Special Cases Tests")
class CalculatorSpecialCasesTest {
    private final Calculator calculator = new Calculator();

    @Test
    @DisplayName("Add same number twice")
    void addSameNumberTwice() {
        assertThat(calculator.add(33, 33)).isEqualTo(66);
    }

    @Test
    @DisplayName("Multiply same number")
    void multiplySameNumber() {
        assertThat(calculator.multiply(12, 12)).isEqualTo(144);
    }

    @Test
    @DisplayName("Subtract same numbers yields zero")
    void subtractSameNumbers() {
        assertThat(calculator.subtract(555, 555)).isEqualTo(0);
    }

    @Test
    @DisplayName("Divide same numbers yields one")
    void divideSameNumbers() {
        assertThat(calculator.divide(888, 888)).isEqualTo(1);
    }

    @Test
    @DisplayName("Add negative to negative")
    void addNegativeToNegative() {
        assertThat(calculator.add(-10, -20)).isEqualTo(-30);
    }

    @Test
    @DisplayName("Multiply two negatives yields positive")
    void multiplyTwoNegatives() {
        assertThat(calculator.multiply(-5, -6)).isEqualTo(30);
    }

    @Test
    @DisplayName("Multiply positive and negative yields negative")
    void multiplyPositiveAndNegative() {
        assertThat(calculator.multiply(7, -4)).isEqualTo(-28);
    }

    @Test
    @DisplayName("Divide two negatives yields positive")
    void divideTwoNegatives() {
        assertThat(calculator.divide(-20, -4)).isEqualTo(5);
    }

    @Test
    @DisplayName("Divide positive by negative yields negative")
    void dividePositiveByNegative() {
        assertThat(calculator.divide(30, -6)).isEqualTo(-5);
    }

    @Test
    @DisplayName("Subtract negative is addition")
    void subtractNegativeIsAddition() {
        assertThat(calculator.subtract(10, -5)).isEqualTo(15);
    }

    @Test
    @DisplayName("Add zero to zero")
    void addZeroToZero() {
        assertThat(calculator.add(0, 0)).isEqualTo(0);
    }

    @Test
    @DisplayName("Multiply zero by zero")
    void multiplyZeroByZero() {
        assertThat(calculator.multiply(0, 0)).isEqualTo(0);
    }

    @Test
    @DisplayName("Subtract zero from zero")
    void subtractZeroFromZero() {
        assertThat(calculator.subtract(0, 0)).isEqualTo(0);
    }

    @Test
    @DisplayName("Subtract from zero yields negative")
    void subtractFromZero() {
        assertThat(calculator.subtract(0, 25)).isEqualTo(-25);
    }
}


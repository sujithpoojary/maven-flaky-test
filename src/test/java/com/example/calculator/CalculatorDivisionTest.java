package com.example.calculator;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class CalculatorDivisionTest {
    private final Calculator calculator = new Calculator();

    @Nested class PositiveDivision {
        @Test void divideExact() { assertThat(calculator.divide(100,5)).isEqualTo(20); }
        @Test void divideNonEvenTruncatesTowardZero() { assertThat(calculator.divide(7,3)).isEqualTo(2); }
        @Test void divideByOne() { assertThat(calculator.divide(999,1)).isEqualTo(999); }
    }

    @Nested class NegativeMixedDivision {
        @Test void divideWithNegativeDividend() { assertThat(calculator.divide(-9,2)).isEqualTo(-4); }
        @Test void divideWithNegativeDivisor() { assertThat(calculator.divide(9,-2)).isEqualTo(-4); }
        @Test void divideNegativeByNegative() { assertThat(calculator.divide(-9,-2)).isEqualTo(4); }
    }

    @Test void divideByZeroThrows() {
        assertThatThrownBy(() -> calculator.divide(1,0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Division by zero");
    }
}


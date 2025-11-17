package com.example.calculator;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class CalculatorMultiplicationTest {
    private final Calculator calculator = new Calculator();

    @Test void multiplySmallPositive() { assertThat(calculator.multiply(7,6)).isEqualTo(42); }
    @Test void multiplyByZero() { assertThat(calculator.multiply(12345,0)).isZero(); }
    @Test void multiplyByOne() { assertThat(calculator.multiply(9876,1)).isEqualTo(9876); }
    @Test void multiplyWithNegative() { assertThat(calculator.multiply(7,-3)).isEqualTo(-21); }
    @Test void multiplyOverflowWrap() { assertThat(calculator.multiply(Integer.MAX_VALUE,2)).isEqualTo(-2); }
}


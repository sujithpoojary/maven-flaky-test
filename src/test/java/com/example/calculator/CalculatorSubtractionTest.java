package com.example.calculator;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class CalculatorSubtractionTest {
    private final Calculator calculator = new Calculator();

    @Test void subtractSmallPositive() { assertThat(calculator.subtract(10,4)).isEqualTo(6); }
    @Test void subtractIntoNegative() { assertThat(calculator.subtract(3,5)).isEqualTo(-2); }
    @Test void subtractZero() { assertThat(calculator.subtract(9,0)).isEqualTo(9); }
    @Test void subtractFromZero() { assertThat(calculator.subtract(0,9)).isEqualTo(-9); }
    @Test void subtractBoundaryUnderflowWrap() { assertThat(calculator.subtract(Integer.MIN_VALUE,1)).isEqualTo(Integer.MAX_VALUE); }
}


package com.example.calculator;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class CalculatorEdgeCasesTest {
    private final Calculator calculator = new Calculator();

    @Test void addMaxValuePlusOneWrap() { assertThat(calculator.add(Integer.MAX_VALUE,1)).isEqualTo(Integer.MIN_VALUE); }
    @Test void subtractMinValueMinusOneWrap() { assertThat(calculator.subtract(Integer.MIN_VALUE,1)).isEqualTo(Integer.MAX_VALUE); }
    @Test void multiplyMaxValueByTwoWrap() { assertThat(calculator.multiply(Integer.MAX_VALUE,2)).isEqualTo(-2); }
    @Test void multiplyMinValueByNegOneWrap() { assertThat(calculator.multiply(Integer.MIN_VALUE,-1)).isEqualTo(Integer.MIN_VALUE); }
    @Test void divideZeroByPositive() { assertThat(calculator.divide(0,5)).isZero(); }
    @Test void chainDifferentOperations() { int r = calculator.add(5,3); r = calculator.subtract(r,4); r = calculator.multiply(r,6); r = calculator.divide(r,2); assertThat(r).isEqualTo(12); }
}


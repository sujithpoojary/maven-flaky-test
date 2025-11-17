package com.example.calculator;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class CalculatorChainingTest {
    private final Calculator calculator = new Calculator();

    @Test void chainAddSubtractMultiply() { int r = calculator.add(2,3); r = calculator.subtract(r,1); r = calculator.multiply(r,4); assertThat(r).isEqualTo(16); }
    @Test void chainAssociativeGrouping() { int a=3,b=4,c=5; int left = calculator.multiply(calculator.add(a,b),c); int right = calculator.add(a,b); right = calculator.multiply(right,c); assertThat(left).isEqualTo(right); }
    @Test void chainDivisionSequenceTruncation() { int r = calculator.divide(20,3); r = calculator.divide(r,2); assertThat(r).isEqualTo(3); }
}


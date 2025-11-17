package com.example.calculator;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class CalculatorNegativeValuesTest {
    private final Calculator calculator = new Calculator();

    @Test void addTwoNegatives() { assertThat(calculator.add(-5,-8)).isEqualTo(-13); }
    @Test void subtractNegativeFromNegative() { assertThat(calculator.subtract(-5,-8)).isEqualTo(3); }
    @Test void multiplyTwoNegatives() { assertThat(calculator.multiply(-5,-8)).isEqualTo(40); }
    @Test void divideNegativeByPositive() { assertThat(calculator.divide(-9,2)).isEqualTo(-4); }
}


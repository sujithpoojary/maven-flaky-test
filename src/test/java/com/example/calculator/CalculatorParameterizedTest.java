package com.example.calculator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.*;

class CalculatorParameterizedTest {
    private final Calculator calculator = new Calculator();

    @ParameterizedTest(name="add {0} + {1} = {2}")
    @CsvSource({
        "0,0,0",
        "1,2,3",
        "-5,8,3",
        "100,-50,50",
        "2147483647,-1,2147483646"
    })
    void parameterizedAddCases(int a, int b, int expected) { assertThat(calculator.add(a,b)).isEqualTo(expected); }

    @ParameterizedTest(name="subtract {0} - {1} = {2}")
    @CsvSource({
        "0,0,0",
        "5,2,3",
        "-5,-8,3",
        "100,-50,150",
        "-10,5,-15"
    })
    void parameterizedSubtractCases(int a, int b, int expected) { assertThat(calculator.subtract(a,b)).isEqualTo(expected); }
}


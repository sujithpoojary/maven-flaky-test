package com.example.calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorUnitTest {
    private final Calculator calculator = new Calculator();

    @Test
    void addTwoNumbers() {
        assertEquals(7, calculator.add(3,4));
    }

    @Test
    void divisionByZeroThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> calculator.divide(1,0));
        assertTrue(ex.getMessage().contains("Division by zero"));
    }
}


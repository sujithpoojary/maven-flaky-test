package com.example.calculator;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the CLI entry point (Main.main).
 * Naming follows convention *IT.java so they are picked up by the Failsafe plugin.
 */
class MainIT {
    private PrintStream originalOut;
    private PrintStream originalErr;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        originalErr = System.err;
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    private String stdout() { return outContent.toString().trim(); }
    private String stderr() { return errContent.toString().trim(); }

    @Test
    @DisplayName("CLI default invocation shows demo and usage")
    void defaultInvocation() {
        Main.main(new String[]{});
        String out = stdout();
        assertTrue(out.contains("Calculator demo:"));
        assertTrue(out.contains("Usage:"));
    }

    @Test
    @DisplayName("CLI addition")
    void addition() {
        Main.main(new String[]{"2","+","3"});
        assertEquals("2 + 3 = 5", stdout());
        assertEquals("", stderr());
    }

    @Test
    @DisplayName("CLI subtraction")
    void subtraction() {
        Main.main(new String[]{"10","-","4"});
        assertEquals("10 - 4 = 6", stdout());
    }

    @Test
    @DisplayName("CLI multiplication")
    void multiplication() {
        Main.main(new String[]{"7","*","6"});
        assertEquals("7 * 6 = 42", stdout());
    }

    @Test
    @DisplayName("CLI division")
    void division() {
        Main.main(new String[]{"8","/","2"});
        assertEquals("8 / 2 = 4", stdout());
    }

    @Test
    @DisplayName("Division by zero error path")
    void divisionByZero() {
        Main.main(new String[]{"1","/","0"});
        assertTrue(stderr().contains("Division by zero"));
        assertEquals("", stdout());
    }

    @Test
    @DisplayName("Unknown operator error path")
    void unknownOperator() {
        Main.main(new String[]{"1","%","2"});
        assertTrue(stderr().contains("Unknown operator"));
        assertEquals("", stdout());
    }

    @Test
    @DisplayName("Invalid number format error path")
    void invalidNumber() {
        Main.main(new String[]{"abc","+","2"});
        assertTrue(stderr().contains("Error:"));
        assertEquals("", stdout());
    }

    @Test
    @DisplayName("Leading/trailing whitespace causes parse error")
    void whitespaceArgs() {
        Main.main(new String[]{" 2","+","3 "});
        assertTrue(stderr().contains("Error:"));
    }

    @Test
    @DisplayName("Large number addition")
    void largeNumbers() {
        Main.main(new String[]{"100000","+","234567"});
        assertEquals("100000 + 234567 = 334567", stdout());
    }
}


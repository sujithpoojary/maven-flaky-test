package com.example.calculator;

import org.junit.jupiter.api.*;
import java.io.*;
import static org.assertj.core.api.Assertions.*;

class MainErrorHandlingIT {
    private PrintStream originalOut; private PrintStream originalErr; private ByteArrayOutputStream outContent; private ByteArrayOutputStream errContent;
    @BeforeEach void setUp(){ originalOut=System.out; originalErr=System.err; outContent=new ByteArrayOutputStream(); errContent=new ByteArrayOutputStream(); System.setOut(new PrintStream(outContent)); System.setErr(new PrintStream(errContent)); }
    @AfterEach void tearDown(){ System.setOut(originalOut); System.setErr(originalErr); }
    private String out(){ return outContent.toString().trim(); } private String err(){ return errContent.toString().trim(); }

    @Test void divisionByZeroError(){ Main.main(new String[]{"1","/","0"}); assertThat(err()).contains("Division by zero"); }
    @Test void unknownOperatorPercent(){ Main.main(new String[]{"1","%","2"}); assertThat(err()).contains("Unknown operator"); }
    @Test void unknownOperatorCaret(){ Main.main(new String[]{"1","^","2"}); assertThat(err()).contains("Unknown operator"); }
    @Test void invalidNumberError(){ Main.main(new String[]{"abc","+","2"}); assertThat(err()).contains("Error:"); }
    @Test void nonIntegerNumberError(){ Main.main(new String[]{"1.5","+","2"}); assertThat(err()).contains("Error:"); }
    @Test void overflowAdditionWrapSilent(){ Main.main(new String[]{String.valueOf(Integer.MAX_VALUE),"+","1"}); assertThat(out()).isEqualTo(Integer.MAX_VALUE + " + 1 = " + Integer.MIN_VALUE); }
}


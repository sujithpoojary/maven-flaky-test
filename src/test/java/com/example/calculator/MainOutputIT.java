package com.example.calculator;

import org.junit.jupiter.api.*;
import java.io.*;
import static org.assertj.core.api.Assertions.*;

class MainOutputIT {
    private PrintStream originalOut; private PrintStream originalErr; private ByteArrayOutputStream outContent; private ByteArrayOutputStream errContent;
    @BeforeEach void setUp(){ originalOut=System.out; originalErr=System.err; outContent=new ByteArrayOutputStream(); errContent=new ByteArrayOutputStream(); System.setOut(new PrintStream(outContent)); System.setErr(new PrintStream(errContent)); }
    @AfterEach void tearDown(){ System.setOut(originalOut); System.setErr(originalErr); }
    private String out(){ return outContent.toString().trim(); } private String err(){ return errContent.toString().trim(); }

    @Test void additionOutputFormat(){ Main.main(new String[]{"2","+","3"}); assertThat(out()).isEqualTo("2 + 3 = 5"); }
    @Test void subtractionOutputFormat(){ Main.main(new String[]{"10","-","4"}); assertThat(out()).isEqualTo("10 - 4 = 6"); }
    @Test void multiplicationOutputFormat(){ Main.main(new String[]{"7","*","6"}); assertThat(out()).isEqualTo("7 * 6 = 42"); }
    @Test void divisionOutputFormat(){ Main.main(new String[]{"8","/","2"}); assertThat(out()).isEqualTo("8 / 2 = 4"); }
    @Test void largeNumberOutputFormat(){ Main.main(new String[]{"100000","+","234567"}); assertThat(out()).isEqualTo("100000 + 234567 = 334567"); }
    @Test void negativeNumbersOutputFormat(){ Main.main(new String[]{"-5","+","-8"}); assertThat(out()).isEqualTo("-5 + -8 = -13"); }
}


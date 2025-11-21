package com.example.calculator;

import org.junit.jupiter.api.*;
import java.io.*;
import static org.assertj.core.api.Assertions.*;

class MainUsabilityIT {
    private PrintStream originalOut; private PrintStream originalErr; private ByteArrayOutputStream outContent; private ByteArrayOutputStream errContent;
    @BeforeEach void setUp(){ originalOut=System.out; originalErr=System.err; outContent=new ByteArrayOutputStream(); errContent=new ByteArrayOutputStream(); System.setOut(new PrintStream(outContent)); System.setErr(new PrintStream(errContent)); }
    @AfterEach void tearDown(){ System.setOut(originalOut); System.setErr(originalErr); }
    private String out(){ return outContent.toString().trim(); } private String err(){ return errContent.toString().trim(); }

    @Test void helpMessageContainsOperators(){ Main.main(new String[]{}); assertThat(out()).contains("Operators:").contains("+").contains("-").contains("*").contains("/"); }
    @Test void usageMessageAfterError(){ Main.main(new String[]{"1"}); assertThat(err()).contains("Error"); }
    @Test void outputNoTrailingWhitespace(){ Main.main(new String[]{"2","+","3"}); assertThat(out()).isEqualTo("2 + 3 = 5"); }
}


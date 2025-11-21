package com.example.calculator;

import org.junit.jupiter.api.*;
import java.io.*;
import static org.assertj.core.api.Assertions.*;

class MainArgsIT {
    private PrintStream originalOut; private PrintStream originalErr; private ByteArrayOutputStream outContent; private ByteArrayOutputStream errContent;
    @BeforeEach void setUp(){ originalOut=System.out; originalErr=System.err; outContent=new ByteArrayOutputStream(); errContent=new ByteArrayOutputStream(); System.setOut(new PrintStream(outContent)); System.setErr(new PrintStream(errContent)); }
    @AfterEach void tearDown(){ System.setOut(originalOut); System.setErr(originalErr); }
    private String out(){ return outContent.toString().trim(); } private String err(){ return errContent.toString().trim(); }

    @Test void noArgsShowsUsage(){ Main.main(new String[]{}); assertThat(out()).contains("Usage:"); }
    @Test void oneArgShowsError(){ Main.main(new String[]{"1"}); assertThat(err()).contains("Error"); }
    @Test void twoArgsShowsError(){ Main.main(new String[]{"1","+"}); assertThat(err()).contains("Error"); }
    @Test void tooManyArgsShowsError(){ Main.main(new String[]{"1","+","2","extra"}); assertThat(err()).contains("Error"); }
    @Test void whitespaceArgsError(){ Main.main(new String[]{" 2","+","3 "}); assertThat(err()).contains("Error"); }
}


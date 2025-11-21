package com.example.calculator;

import org.junit.jupiter.api.*;
import java.io.*;
import static org.assertj.core.api.Assertions.*;

class MainStressIT {
    private PrintStream originalOut; private PrintStream originalErr; private ByteArrayOutputStream outContent; private ByteArrayOutputStream errContent;
    @BeforeEach void setUp(){ originalOut=System.out; originalErr=System.err; outContent=new ByteArrayOutputStream(); errContent=new ByteArrayOutputStream(); System.setOut(new PrintStream(outContent)); System.setErr(new PrintStream(errContent)); }
    @AfterEach void tearDown(){ System.setOut(originalOut); System.setErr(originalErr); }
    private String out(){ return outContent.toString(); } private String err(){ return errContent.toString(); }

    @Test void stressHundredsAdditions(){ for(int i=0;i<200;i++){ Main.main(new String[]{String.valueOf(i),"+",String.valueOf(i+1)}); } assertThat(err()).isEmpty(); assertThat(out().split("\\n")).hasSizeGreaterThanOrEqualTo(200); }
    @Test void stressMixedOpsLoop(){ for(int i=1;i<=100;i++){ Main.main(new String[]{String.valueOf(i),"/","1"}); Main.main(new String[]{String.valueOf(i),"*","2"}); } assertThat(err()).doesNotContain("Unknown operator"); }
}


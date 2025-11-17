package com.example.calculator;

import org.junit.jupiter.api.*;
import java.io.*;
import static org.assertj.core.api.Assertions.*;

class MainSequentialIT {
    private PrintStream originalOut; private PrintStream originalErr; private ByteArrayOutputStream outContent; private ByteArrayOutputStream errContent;
    @BeforeEach void setUp(){ originalOut=System.out; originalErr=System.err; outContent=new ByteArrayOutputStream(); errContent=new ByteArrayOutputStream(); System.setOut(new PrintStream(outContent)); System.setErr(new PrintStream(errContent)); }
    @AfterEach void tearDown(){ System.setOut(originalOut); System.setErr(originalErr); }
    private String out(){ return outContent.toString().trim(); } private String err(){ return errContent.toString().trim(); }

    @Test void repeatedInvocationsMaintainIndependence(){ Main.main(new String[]{"2","+","3"}); Main.main(new String[]{"4","+","5"}); assertThat(out()).contains("2 + 3 = 5"); assertThat(out()).contains("4 + 5 = 9"); }
    @Test void mixedOperationsSequential(){ Main.main(new String[]{"10","-","4"}); Main.main(new String[]{"3","*","4"}); Main.main(new String[]{"8","/","2"}); assertThat(out()).contains("10 - 4 = 6"); assertThat(out()).contains("3 * 4 = 12"); assertThat(out()).contains("8 / 2 = 4"); }
    @Test void errorDoesNotLeakState(){ Main.main(new String[]{"1","/","0"}); Main.main(new String[]{"2","+","3"}); assertThat(err()).contains("Division by zero"); assertThat(out()).contains("2 + 3 = 5"); }
}


package com.example.calculator;

import org.junit.jupiter.api.*;
import java.io.*;
import java.util.concurrent.*;
import static org.assertj.core.api.Assertions.*;

class MainConcurrencyIT {
    private PrintStream originalOut; private PrintStream originalErr; private ByteArrayOutputStream outContent; private ByteArrayOutputStream errContent;
    @BeforeEach void setUp(){ originalOut=System.out; originalErr=System.err; outContent=new ByteArrayOutputStream(); errContent=new ByteArrayOutputStream(); System.setOut(new PrintStream(outContent)); System.setErr(new PrintStream(errContent)); }
    @AfterEach void tearDown(){ System.setOut(originalOut); System.setErr(originalErr); }
    private String out(){ return outContent.toString(); } private String err(){ return errContent.toString(); }

    @Test void parallelInvocationsAddition() throws Exception {
        ExecutorService exec = Executors.newFixedThreadPool(8);
        for(int i=0;i<100;i++){ final int v=i; exec.submit(() -> Main.main(new String[]{String.valueOf(v),"+",String.valueOf(v+1)})); }
        exec.shutdown(); exec.awaitTermination(5, TimeUnit.SECONDS);
        assertThat(err()).doesNotContain("Error");
    }

    @Test void parallelInvocationsMixed() throws Exception {
        ExecutorService exec = Executors.newFixedThreadPool(8);
        for(int i=1;i<=100;i++){ final int v=i; exec.submit(() -> Main.main(new String[]{String.valueOf(v),"/","1"})); exec.submit(() -> Main.main(new String[]{String.valueOf(v),"*","2"})); }
        exec.shutdown(); exec.awaitTermination(5, TimeUnit.SECONDS);
        assertThat(err()).doesNotContain("Error");
    }
}


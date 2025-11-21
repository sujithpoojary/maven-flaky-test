package com.example.calculator;

import org.junit.jupiter.api.*;
import java.io.*;
import java.util.Random;
import static org.assertj.core.api.Assertions.*;

class MainRandomizedIT {
    private PrintStream originalOut; private PrintStream originalErr; private ByteArrayOutputStream outContent; private ByteArrayOutputStream errContent; private final Random rnd = new Random(54321);
    @BeforeEach void setUp(){ originalOut=System.out; originalErr=System.err; outContent=new ByteArrayOutputStream(); errContent=new ByteArrayOutputStream(); System.setOut(new PrintStream(outContent)); System.setErr(new PrintStream(errContent)); }
    @AfterEach void tearDown(){ System.setOut(originalOut); System.setErr(originalErr); }
    private String out(){ return outContent.toString(); } private String err(){ return errContent.toString(); }

    @Test void randomizedSeededOperations(){ for(int i=0;i<50;i++){ int a=rnd.nextInt(1000)-500; int b=rnd.nextInt(1000)-500; Main.main(new String[]{String.valueOf(a),"+",String.valueOf(b)}); Main.main(new String[]{String.valueOf(a),"-",String.valueOf(b)}); Main.main(new String[]{String.valueOf(a),"*",String.valueOf(b)}); if(b!=0) Main.main(new String[]{String.valueOf(a),"/",String.valueOf(b==0?1:b)}); } assertThat(err()).doesNotContain("Unknown operator"); }
}

